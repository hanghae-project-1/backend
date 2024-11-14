package com.example.demo.domain.region.exception;

import com.example.demo.common.exception.Error;
import org.springframework.http.HttpStatus;

public class NotFoundRegionException extends RegionException{

    public NotFoundRegionException(){
        super(Error.NOT_FOUND_REGION, HttpStatus.NOT_FOUND);
    }

}
