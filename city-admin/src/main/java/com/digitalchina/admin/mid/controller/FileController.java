package com.digitalchina.admin.mid.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import cn.hutool.core.util.NumberUtil;
import com.digitalchina.admin.remoteservice.CityEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.digitalchina.common.dto.FileDto;
import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.remoteservice.ThirdRemoteService;
import com.digitalchina.modules.security.TokenProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.coobird.thumbnailator.Thumbnails;

/**
 * 文件服务
 *
 * @author warrior
 * @since 2019年9月23日
 */

@Api(tags = "文件上传和下载")
@RestController
@RequestMapping("file")
@Slf4j
public class FileController {

    @Autowired
    private ThirdRemoteService trs;

    @Autowired(required = false)
    private TokenProvider tp;

    @Resource
    private CityEventService cityEventService;

    /**
     * 文件上传
     *
     * @param file
     * @return 文件唯一ID
     * @throws IOException
     */
    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "文件上传")
//	@Authorize
    public RespMsg<String> upload(@RequestParam MultipartFile file) throws IOException {
        return RespMsg.ok(trs.upload(file));

    }

    @GetMapping("get")
    @ApiOperation(value = "文件下载")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "文件ID", dataType = "String", required = true),
//			@ApiImplicitParam(name = "token", value = "用户token", dataType = "String", required = true),
            @ApiImplicitParam(name = "compress", value = "1:需要压缩", dataType = "Integer")})
    public ResponseEntity<byte[]> downloadFile(@RequestParam(required = true) String id,
                                               Integer compress) throws IOException {
//		if (tp.getUserDetail(token) == null) {
//			throw new ServiceException(401, "没有权限访问！");
//		}
        log.info("download {}", id);
        if (NumberUtil.isLong(id)) {
            try {
                return cityEventService.downloadAttachement(Long.parseLong(id),true);
            } catch (Exception e) {
                log.error("download {} error: {}", id, e.getMessage());
            }
        }

        FileDto file = trs.download(id);

        byte[] body = file.getContent();
        if (compress != null && 1 == compress) {
            ByteArrayInputStream intputStream = new ByteArrayInputStream(body);
            Thumbnails.Builder<? extends InputStream> builder = Thumbnails.of(intputStream).scale(0.3f)
                    .outputQuality(0.3f);
            BufferedImage bufferedImage = builder.asBufferedImage();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            body = baos.toByteArray();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", URLEncoder.encode(file.getName(), "utf-8"));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 返回数据
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(file.getContent(), headers, HttpStatus.OK);
        return entity;

    }
}
