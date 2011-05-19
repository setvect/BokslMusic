package com.setvect.bokslmusic.ui.server;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.setvect.bokslmusic.ui.client.service.AlbumService;
import com.setvect.bokslmusic.ui.shared.model.AlbumArticleModel;
import com.setvect.bokslmusic.ui.shared.model.FolderModel;

/**
 * 
 */
@Service("AlbumService")
public class AlbumServiceImpl implements AlbumService {

	public List<AlbumArticleModel> getFolderChildren(AlbumArticleModel model) {
		List<AlbumArticleModel> result = new ArrayList<AlbumArticleModel>();
		if (model == null) {
			FolderModel a = new FolderModel("안녕a", "hi");
			result.add(a);
		}
		else {
			for (int i = 0; i < 50; i++) {
				AlbumArticleModel a = new AlbumArticleModel("안녕" + i, 100, "hi11");
				result.add(a);
			}
		}
		return result;
	}


	public boolean addAlbum(String albumName) {
		return false;
	}

}
