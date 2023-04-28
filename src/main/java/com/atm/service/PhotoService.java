package com.atm.service;

import com.atm.model.Photo;

public interface PhotoService {
	public Photo getPhotoById(Long id);
	public Photo addPhoto(String imageName,Long uid);
}
