package com.setvect.bokslmusic.ui.server;

import java.util.List;

import org.springframework.stereotype.Service;

import com.extjs.gxt.ui.client.data.RemoteSortTreeLoadConfig;
import com.setvect.bokslmusic.ui.client.service.FileService;
import com.setvect.bokslmusic.ui.shared.model.AlbumArticleModel;

/**
 * 
 */
@Service("FileService")
public class FileServiceImpl implements FileService {

	public List<AlbumArticleModel> getFolderChildren(AlbumArticleModel model) {
		return null;
	}

	public List<AlbumArticleModel> getFolderChildren(RemoteSortTreeLoadConfig loadConfig) {
		return null;
	}

}
