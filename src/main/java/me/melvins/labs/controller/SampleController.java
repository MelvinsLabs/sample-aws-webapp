/*
 * 
 */

package me.melvins.labs.controller;

import me.melvins.labs.capability.Capability;
import me.melvins.labs.exception.KnownException;
import me.melvins.labs.exception.RequestHeaderValidationException;
import me.melvins.labs.exception.UnknownException;
import me.melvins.labs.exception.handling.ErrorCode;
import me.melvins.labs.pojo.models.DomainModel;
import me.melvins.labs.pojo.vo.RequestHeaderVO;
import me.melvins.labs.pojo.vo.ResponseVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static me.melvins.labs.utils.HeaderUtils.transformRequestHeader;
import static me.melvins.labs.utils.HeaderUtils.validateRequestHeader;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Sample Controller
 *
 * @author Mels
 */
@RestController
@RequestMapping(value = "Sample")
public class SampleController {

    @Autowired
    private Capability capabilityImpl;

    private static final Logger LOGGER =
            LogManager.getLogger(SampleController.class, new MessageFormatMessageFactory());

    /**
     * Sample GET Operation.
     *
     * @param headers  Request Headers
     * @param hashKey  {@link RequestParam} HashKey
     * @param rangeKey {@link RequestParam} RangeKey
     * @return {@code ResponseVO} wrapped as a {@link ResponseEntity}
     */
    @RequestMapping(value = "Read", method = RequestMethod.GET)
    public ResponseEntity<ResponseVO> read(@RequestHeader Map<String, Object> headers,
                                              @RequestParam(name = "hashKey") String hashKey,
                                              @RequestParam(name = "rangeKey") String rangeKey) {
        LOGGER.debug("Sample Read HashKey={0} RangeKey={1}", hashKey, rangeKey);

        ResponseVO responseVO = null;
        try {
            //RequestHeaderVO requestHeaderVO = transformRequestHeader(headers);

            //validateRequestHeader(requestHeaderVO);

            String itemFromDynamo = capabilityImpl.read(hashKey, rangeKey);

            responseVO = new ResponseVO();
            responseVO.setItem(itemFromDynamo);

        } catch (Exception ex) {
            handleExceptions(ex);
        }

        return new ResponseEntity<>(responseVO, null, HttpStatus.OK);
    }

    /**
     * Generic method to handle Exception from the Controller Operations.
     *
     * @param ex Exception to handle.
     */
    private void handleExceptions(Exception ex) {

        if (ex instanceof RequestHeaderValidationException) {
            throw (RequestHeaderValidationException) ex;

        } else if (ex instanceof KnownException) {
            throw (KnownException) ex;

        } else {
            ErrorCode errorCode = ErrorCode.EC1000;
            LOGGER.error(errorCode.toString(), ex);
            throw new UnknownException(INTERNAL_SERVER_ERROR, errorCode, ex);
        }
    }

}
