/*
package com.sshl.rest.admin.restful.media.handler.impl.api;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sshl.core.context.CoreContext;
import com.sshl.core.result.IResultCode;
import com.sshl.core.result.ResultCode;
import com.sshl.core.result.ResultVO;
import com.sshl.core.utils.FileUtils;
import com.sshl.core.utils.Md5;
import com.sshl.fastdfs.starter.FastdfsTemplate;
import com.sshl.provider.media.api.constants.MediaDubboConstants;
import com.sshl.provider.media.api.constants.MediaType;
import com.sshl.provider.media.api.dto.FileDTO;
import com.sshl.provider.media.api.dto.MediaDTO;
import com.sshl.provider.media.api.dto.TemplateDTO;
import com.sshl.provider.media.api.service.IFileDubboService;
import com.sshl.provider.media.api.service.IMediaDubboService;
import com.sshl.provider.media.api.service.ITemplateDubboService;
import com.sshl.provider.terminal.api.constants.TerminalDubboConstants;
import com.sshl.provider.terminal.api.dto.ScreenDTO;
import com.sshl.provider.terminal.api.dto.TerminalScreenDTO;
import com.sshl.provider.terminal.api.service.IScreenDubboService;
import com.sshl.provider.terminal.api.service.ITerminalScreenDubboService;
import com.sshl.rest.admin.constants.AdminRedissonConstants;
import com.sshl.rest.admin.constants.FastdfsConstants;
import com.sshl.rest.admin.rabbitmq.RabbitmqTemplate;
import com.sshl.rest.admin.restful.media.handler.api.IBJTemplateHandlerService;
import com.sshl.rest.admin.restful.media.request.api.TemplateUpdateRequest;
import com.sshl.rest.admin.restful.media.utils.FileUtil;
import com.sshl.rest.admin.schedule.DelayCreateProgramPublishSchedule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

*/
/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Bobby
 * @Date: 2021/12/16/16:11
 * @Description:
 *//*

@Slf4j
@Service
public class BJTemplateHandlerServiceImpl implements IBJTemplateHandlerService {

    @Value("${app.uploading-file}")
    private String resourceDir;

    @Value("${xml.md5Key}")
    private Integer verifyKey;

    @DubboReference(group = MediaDubboConstants.GROUP, version = MediaDubboConstants.VERSION)
    private ITemplateDubboService dubboService;

    @DubboReference(group = TerminalDubboConstants.GROUP, version = TerminalDubboConstants.VERSION)
    private IScreenDubboService screenDubboService;

    @DubboReference(group = TerminalDubboConstants.GROUP, version = TerminalDubboConstants.VERSION)
    private ITerminalScreenDubboService terminalScreenDubboService;

    @DubboReference(group = MediaDubboConstants.GROUP, version = MediaDubboConstants.VERSION)
    private IMediaDubboService mediaDubboService;

    @DubboReference(group = MediaDubboConstants.GROUP, version = MediaDubboConstants.VERSION)
    private IFileDubboService fileDubboService;

    @Autowired
    private FastdfsTemplate fastdfsTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RabbitmqTemplate rabbitmqTemplate;

    @Override
    public ResultVO<Void> updateTemplate(TemplateUpdateRequest request) throws Exception {
        long publishVersion = 0L;
        ResultVO<TemplateDTO> oldTemplate = this.dubboService.get(request.getId());
        if (Objects.isNull(oldTemplate.getData())) {
            ResultVO.failed(ResultCode.DATA_NON_EXISTENT, "????????????????????????????????????ID");
        }
        //??????????????????
        JSONObject extraArgus = new JSONObject();
        //???????????????????????????
        JSONObject extensionArgus = null;
        for (int i = 0; i < request.getArgus().size(); i++) {
            if (request.getArgus().getJSONObject(i).getString("type").equals("Extension")) {
                extensionArgus = request.getArgus().getJSONObject(i);
                extraArgus.put("extraJson",extensionArgus);
                request.getArgus().remove(i);
            }
        }
        String argusStr = request.getArgus().toJSONString();
        String oldArgusStr = oldTemplate.getData().getArgus();
        String extraArgusStr = "";
        if(null!=request.getExtraArgus() && request.getExtraArgus().size()>0){
            extraArgusStr = request.getExtraArgus().toJSONString();
        }
        String oldExtraArgusStr = oldTemplate.getData().getExtraArgus();
        Integer height = request.getHeight();
        //?????????????????????????????????????????????
        if(null!=extensionArgus && !extensionArgus.isEmpty()){
            height = 1280 - Integer.parseInt(extensionArgus.getString("h"));
        }
        extraArgus.put("extra",extraArgusStr);

        // ??????????????????checksum
        String checksum = DigestUtils.md5DigestAsHex(argusStr.getBytes());
        // ?????????????????????checksum
        String extraChecksum = DigestUtils.md5DigestAsHex(extraArgusStr.getBytes());
        // ??????????????????checksum
        String oldChecksum = DigestUtils.md5DigestAsHex(Optional.ofNullable(oldArgusStr).orElse("").getBytes());
        // ?????????????????????checksum
        String oldExtraArgus = "";
        String oldExtensionArgus = "";
        if (!StringUtils.isEmpty(oldExtraArgusStr)) {
            JSONObject extraJsonArgus = JSONObject.parseObject(oldExtraArgusStr);
            if(null!=extraJsonArgus.getJSONObject("extra")&&!extraJsonArgus.getJSONObject("extra").isEmpty()){
                oldExtraArgus = extraJsonArgus.getJSONObject("extra").toJSONString();
            }
            if(null!=extraJsonArgus.getJSONObject("extraJson")&&!extraJsonArgus.getJSONObject("extraJson").isEmpty()) {
                oldExtensionArgus = extraJsonArgus.getJSONObject("extraJson").toJSONString();
            }
        }
        String oldExtraChecksum = DigestUtils.md5DigestAsHex(Optional.ofNullable(oldExtraArgus).orElse("").getBytes());
        //???????????????????????????????????????????????????
        if (!checksum.equals(oldChecksum) || !extraChecksum.equals(oldExtraChecksum)) {
            String extensionArgusChecksum = DigestUtils.md5DigestAsHex(Optional.ofNullable(extensionArgus.toJSONString()).orElse("").getBytes());

            String oldExtensionArgusChecksum = DigestUtils.md5DigestAsHex(oldExtensionArgus.getBytes());
            //??????????????????????????????
            if (!extensionArgusChecksum.equals(oldExtensionArgusChecksum) || !extraChecksum.equals(oldExtraChecksum)) {
                //TODO ?????????????????????
                if (!extensionArgus.isEmpty() && !extensionArgusChecksum.equals(oldExtensionArgusChecksum)) {
                    //1???????????????????????? id???0????????????????????????????????????
                    ResultVO<TemplateDTO> extraTemplate = this.dubboService.get(0l);
                    if (Objects.isNull(oldTemplate.getData())) {
                        ResultVO.failed(ResultCode.DATA_NON_EXISTENT, "????????????????????????????????????????????????");
                    }
                    //???????????????????????????
                    TemplateDTO dto = new TemplateDTO();
                    dto.setId(0l);
                    dto.setCorpCode(request.getCorpCode());
                    JSONArray array = new JSONArray();
                    array.add(extensionArgus);
                    dto.setArgus(array.toJSONString());
                    dto.setReview("0");
                    dto.setType(extensionArgus.getString("w") + "*" + extensionArgus.getString("h"));
                    dto.setHeight(extensionArgus.getString("h"));
                    dto.setRevision(extraTemplate.getData().getRevision());
                    IResultCode code = dubboService.modifyTemplate(dto);
                    if (code.getCode() != ResultCode.SUCCESS.getCode()) {
                        return ResultVO.failed(code.getCode(), code.getMsg());
                    }
                    //2?????????????????? id???3????????????????????????????????????1?????????
                    //???????????????????????????????????????
                    ResultVO<ScreenDTO> screen = screenDubboService.get(3l);
                    //?????????????????????????????????????????????????????????????????????
                    if (screen.getData() != null) {
                        if (!screen.getData().getWidth().equals(3*(Integer.parseInt(extensionArgus.getString("h"))/2)))
                        {
                            ScreenDTO screenDTO = new ScreenDTO();
                            screenDTO.setCorpCode(request.getCorpCode());
                            //?????????????????????
                            screenDTO.setId(3l);
                            screenDTO.setHeight(1080);
                            screenDTO.setWidth(3*(Integer.parseInt(extensionArgus.getString("h"))/2));
                            screenDTO.setUseDefault(false);
                            screenDubboService.updateScreen(screenDTO);
                            //???????????????
                            screenDTO.setId(1l);
                            screenDTO.setWidth(1920 - 3*(Integer.parseInt(extensionArgus.getString("h"))/2));
                            screenDubboService.updateScreen(screenDTO);
                        }
                    }
                    //3????????????????????? ????????????????????????????????????1080*1920???720*1320 ????????????
                    TerminalScreenDTO terminalScreenDTO = new TerminalScreenDTO();
                    terminalScreenDTO.setCorpCode(request.getCorpCode());
                    terminalScreenDTO.setHeight(1080);
                    terminalScreenDTO.setWidth(3*(Integer.parseInt(extensionArgus.getString("h"))/2));
                    terminalScreenDubboService.batchUpdateLayoutTemplate(terminalScreenDTO);
                }
                //TODO 4??????????????????????????????????????????????????? ????????????????????????????????????

            }

            //TODO ??????????????????????????????
            if (!checksum.equals(oldChecksum)) {
                //
                this.redissonClient.getSet(AdminRedissonConstants.PUBLISH_TEMPLATE).add(request.getId());
                publishVersion = this.redissonClient.getAtomicLong(AdminRedissonConstants.PUBLISH_SEQUENCE).incrementAndGet();
                JSONObject meidaJson = new JSONObject();
                for (int i = 0; i < request.getArgus().size(); i++) {
                    //????????????????????????????????????????????????????????????
                    if (request.getArgus().getJSONObject(i).getString("type").equals("Badge") || request.getArgus().getJSONObject(i).getString("type").equals("ImageText")) {
                        JSONObject mediaJson = request.getArgus().getJSONObject(i).getJSONObject("media");
                        if (null == mediaJson || mediaJson.isEmpty()) {
                            continue;
                        }
                        String areaId = String.format("%s%s%s",request.getArgus().getJSONObject(i).getString("type"),"-",request.getArgus().getJSONObject(i).getString("index"));
                        meidaJson.put(areaId,mediaJson.getLong("id"));
                        // ??????????????????????????????
                        ResultVO<MediaDTO> mediaResult = this.mediaDubboService.get(mediaJson.getLong("id"));
                        if (!mediaResult.isSuccess() && mediaResult.getCode() != ResultCode.DATA_NON_EXISTENT.getCode()) {
                            return ResultVO.failed(ResultCode.OPERATION_FAILED, "????????????????????????");
                        }
                        if (!Objects.isNull(mediaResult.getData())) {
                            continue;
                        }
                        if (mediaJson.getInteger("type") == MediaType.IMAGE.getType()) {
                            // ??????
                            String extName = FilenameUtils.getExtension(mediaJson.getString("url"));
                            File downloadFile = new File(this.resourceDir, mediaJson.getString("name") + "." + extName);
                            log.info("???????????????????????????:{}, ??????:{}", mediaJson.getString("url"), downloadFile.getAbsolutePath());
                            long logoSize = HttpUtil.downloadFile(mediaJson.getString("url"), downloadFile);
                            if (logoSize <= 0) {
                                return ResultVO.failed(ResultCode.OPERATION_FAILED, "???????????????????????????");
                            }
                            if (!StringUtils.isEmpty(mediaJson.getString("checksum"))) {
                                String fileChecksum = org.apache.commons.codec.digest.DigestUtils.md5Hex(new FileInputStream(downloadFile));
                                if (!mediaJson.getString("checksum").equals(fileChecksum)) {
                                    log.error("????????????ID:`{}`??????????????????, ???????????????:`{}`, ?????????????????????:`{}`", mediaJson.getLong("id"), mediaJson.getString("checksum"), fileChecksum);
                                    return ResultVO.failed(ResultCode.UPDATE_FAILED, "?????????????????????????????????");
                                }
                            } else {
                                log.error("??????ID:`{}`??????????????????, ???????????????????????????", mediaJson.getLong("id"));
                            }
                            // ??????????????????
                            FileDTO file = new FileDTO();
                            file.setCorpCode(request.getCorpCode());
                            file.setChecksum(new Md5(verifyKey).ComputeFileMd5(downloadFile.getAbsolutePath()));
                            file.setType(mediaJson.getInteger("type"));
                            file.setName(mediaJson.getString("name"));
                            file.setLength(downloadFile.length());
                            file.setFormat(extName);
                            file.setVideoFormat("");
                            file.setAudioFormat("");
                            file.setDuration(0L);
                            // ??????????????????
                            MediaDTO media = new MediaDTO();
                            media.setCorpCode(request.getCorpCode());
                            media.setId(mediaJson.getLong("id"));
                            media.setName(mediaJson.getString("name"));
                            media.setOwnerUserId(CoreContext.getCoreInfo().getUserId());
                            media.setState(1);
                            media.setType(mediaJson.getInteger("type"));
                            media.setStartDate(LocalDate.now());
                            media.setEndDate(LocalDate.parse("2037-12-31"));
                            media.setAuditState(3);
                            media.setAuditReason("??????????????????");
                            media.setAuditTime(LocalDateTime.now());
                            media.setAuditUserId(CoreContext.getCoreInfo().getUserId());
                            media.setFolderId(0L);
                            media.setRefCount(0);
                            media.setRevision(1);
                            JSONObject argusObject = new JSONObject();
                            argusObject.put("fileSize", FileUtils.getFileSizeKb((int) downloadFile.length()));
                            argusObject.put("content", mediaJson.getString("url"));
                            argusObject.put("attributes", new JSONObject());
                            argusObject.put("extra", mediaJson.getString("extra"));
                            media.setArgus(argusObject.toString());
                            int types = FileUtil.judgeFileType(extName);
                            try (InputStream in = new FileInputStream(downloadFile)) {
                                BufferedImage bufferedImage = ImageIO.read(in);
                                if (bufferedImage != null) {
                                    // ??????????????????
                                    file.setWidth(bufferedImage.getWidth());
                                    file.setHeight(bufferedImage.getHeight());
                                    HashMap<String, Object> pictureMap = fastdfsTemplate.uploadImageAndThumbnails(downloadFile.getPath(), types);
                                    file.setPath(pictureMap.get("path").toString());
                                    ResultVO<FileDTO> fileResult = this.fileDubboService.save(file);
                                    if (!fileResult.isSuccess() || Objects.isNull(fileResult.getData())) {
                                        return ResultVO.failed(ResultCode.OPERATION_FAILED, "????????????????????????");
                                    }
                                    media.setThumbnailFile(pictureMap.get("thumbnails").toString());
                                    media.setFileId(fileResult.getData().getId());
                                    mediaResult = this.mediaDubboService.save(media);
                                    if (!mediaResult.isSuccess() || Objects.isNull(mediaResult.getData())) {
                                        return ResultVO.failed(ResultCode.OPERATION_FAILED, "????????????????????????");
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                log.error("??????ID:`{}`??????????????????{}", mediaJson.getLong("id"), e.getMessage());
                                return ResultVO.failed(ResultCode.UPDATE_FAILED, "??????????????????");
                            }
                            // ?????????????????????
                            downloadFile.delete();
                        } else if (mediaJson.getInteger("type") == MediaType.LOCAL_TEXT.getType() || mediaJson.getInteger("type") == MediaType.TEXT.getType()) {
                            JSONObject argusObject = new JSONObject();
                            String text = mediaJson.getString("url");
//                            JSONObject textObject = JSONObject.parseObject(text);
//                            String content = textObject.getString("content");
                            // ??????????????????
                            File destFile = new File(resourceDir, System.currentTimeMillis() + ".txt");
                            cn.hutool.core.io.FileUtil.writeString(text, destFile, "UTF-8");
                            // ??????????????????
                            HashMap<String, Object> uploadResult = this.fastdfsTemplate.uploadFile(destFile.getAbsolutePath(), FastdfsConstants.StorePath.TEXT);
                            String path = (String) uploadResult.get("path");
                            // ??????????????????
                            FileDTO file = new FileDTO();
                            file.setCorpCode(request.getCorpCode());
                            file.setChecksum(new Md5(verifyKey).ComputeFileMd5(destFile.getAbsolutePath()));
                            file.setPath(path);
                            file.setType(MediaType.LOCAL_TEXT.getType());
                            file.setName(mediaJson.getString("name") + System.currentTimeMillis());
                            file.setLength(destFile.length());
                            file.setFormat("text");
                            file.setVideoFormat("");
                            file.setAudioFormat("");
                            file.setWidth(0);
                            file.setHeight(0);
                            file.setDuration(0L);
                            ResultVO<FileDTO> fileResult = this.fileDubboService.save(file);
                            if (!fileResult.isSuccess() || Objects.isNull(fileResult.getData())) {
                                return ResultVO.failed(ResultCode.OPERATION_FAILED, "????????????????????????");
                            }
                            // ??????????????????
                            MediaDTO media = new MediaDTO();
                            media.setCorpCode(request.getCorpCode());
                            media.setFileId(fileResult.getData().getId());
                            media.setId(mediaJson.getLong("id"));
                            media.setName(mediaJson.getString("name"));
                            media.setOwnerUserId(CoreContext.getCoreInfo().getUserId());
                            media.setState(1);
                            media.setType(MediaType.LOCAL_TEXT.getType());
                            media.setStartDate(LocalDate.now());
                            media.setEndDate(LocalDate.parse("2037-12-31"));
                            media.setAuditState(3);
                            media.setAuditReason("??????????????????");
                            media.setAuditTime(LocalDateTime.now());
                            media.setAuditUserId(CoreContext.getCoreInfo().getUserId());
                            media.setFolderId(0L);
                            media.setRefCount(0);
                            media.setRevision(1);
                            argusObject.put("fileSize", FileUtils.getFileSizeKb((int) destFile.length()));
                            argusObject.put("content", text);
                            argusObject.put("attributes", new JSONObject());
                            argusObject.put("extra", mediaJson.getJSONObject("extra").toJSONString());
                            media.setArgus(argusObject.toString());
                            mediaResult = this.mediaDubboService.save(media);
                            if (!mediaResult.isSuccess() || Objects.isNull(mediaResult.getData())) {
                                return ResultVO.failed(ResultCode.OPERATION_FAILED, "????????????????????????");
                            }
                            // ??????????????????
                            destFile.delete();
                        }

                    }
                }
                extraArgus.put("media",meidaJson);

                //??????????????????
                TemplateDTO dto = new TemplateDTO();
                BeanUtils.copyProperties(request, dto);
                //????????????
                dto.setType(request.getWidth() + "*" + height);
                dto.setReview("0");
                dto.setRevision(oldTemplate.getData().getRevision());
                dto.setHeight(height+"");
                dto.setExtraArgus(extraArgus.toJSONString());
                dto.setArgus(request.getArgus().toJSONString());
                IResultCode code = dubboService.modifyTemplate(dto);
                if (code.getCode() == ResultCode.SUCCESS.getCode()) {
                    //???????????????
                    log.error("?????????????????????, ???????????????????????????, ???????????????????????????:`{}`", publishVersion);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("corpCode", request.getCorpCode());
                    jsonObject.put("publishType", 1);
                    jsonObject.put("code", "0000000000");
                    DelayCreateProgramPublishSchedule task = new DelayCreateProgramPublishSchedule(this.rabbitmqTemplate, this.redissonClient, jsonObject, publishVersion);
                    ProgramPublishHandlerServiceImpl.scheduled.schedule(task, ProgramPublishHandlerServiceImpl.delay, TimeUnit.SECONDS);
                    return ResultVO.success(null);
                }
                return ResultVO.failed(code.getCode(), code.getMsg());
            }

        }
        return ResultVO.success(null);
    }
}
*/
