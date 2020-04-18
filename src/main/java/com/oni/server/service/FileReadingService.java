package com.oni.server.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.oni.server.model.impl.User;

public interface FileReadingService {

	public List<User> readFile(InputStream inputStream) throws IOException;

}
