package com.setvect.bokslmusic.db.h2;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.setvect.bokslmusic.db.common.AbstractMusicDao;

/**
 * H2 DB용 코드 DAO
 * 
 * @version $Id$
 */
@Service
@Qualifier("H2")
public class H2MusicDao extends AbstractMusicDao {
}
