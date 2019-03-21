package com.jamie.sample.SOCKET;

import com.jamie.sample.SOCKET.handler.MessageHandler;
import com.jamie.sample.SOCKET.model.ResponseOverlays;
import com.jamie.sample.UTIL.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by jeonjaebum on 2018. 1. 4..
 */
@RestController
@RequestMapping("REST")
public class RESTController {

    @Inject
    String uploadDir;

    //private static Logger log = LoggerFactory.getLogger(RESTController.class);

    @RequestMapping(value = "/GET_USERS_ID", method = RequestMethod.GET)
    public ResponseEntity<List<String>> GET_USERS_ID() {
        Map<String, String> ids =  MessageHandler.getIds();

        List<String> result = ids.values().stream().collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/IMAGE_SEND", method = RequestMethod.POST)
    public ResponseOverlays<String> IMAGE_SEND(MultipartFile file, String id) {
        String result = "";
        try {
            result = FileUtils.upload(uploadDir,id+"_"+file.getOriginalFilename(),file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseOverlays<>(200,null,result);
    }
}
