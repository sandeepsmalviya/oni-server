package com.oni.server.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oni.server.model.UserModel;
import com.oni.server.model.impl.User;
import com.oni.server.repository.UserRepository;
import com.oni.server.service.FileReadingService;
import com.oni.server.service.impl.util.ApachePOIExcelReaderUtil;

@Service
public class ExcelFileReadingService implements FileReadingService {

	@Autowired
	ApachePOIExcelReaderUtil excelReaderUtil;

	@Override
	public List<User> readFile(InputStream inputStream) throws IOException {
		List<User> list = excelReaderUtil.processExcelFile(inputStream);
		return list;
	}

}
