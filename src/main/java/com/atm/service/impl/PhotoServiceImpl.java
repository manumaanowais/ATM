package com.atm.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atm.model.Photo;
import com.atm.repo.PhotoRepository;
import com.atm.service.PhotoService;


@Service
public class PhotoServiceImpl implements PhotoService{

	@Autowired
	private PhotoRepository photoRepository;
	
	@Override
	public Photo getPhotoById(Long id) {
		return photoRepository.findByUid(id);
	}

	@Override
	public Photo addPhoto(String imageName,Long uid) {
		Photo photo1 = new Photo();
		photo1.setUid(uid);
		photo1.setPhoto("C:\\NetCracker\\NumaanImages\\"+imageName);
		return photoRepository.save(photo1);
	}

}
