package com.setvect.bokslmusic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.setvect.bokslmusic.extract.Mp3AudioMetadataTestCase;
import com.setvect.bokslmusic.extract.MusicExtractorTestCase;
import com.zuch.music.util.AlSongMetadataTestCase;
import com.zuch.music.util.LyricUtilTestCase;
import com.zuch.music.util.Md5UtilTestCase;

@RunWith(Suite.class)
@Suite.SuiteClasses({ Mp3AudioMetadataTestCase.class, MusicExtractorTestCase.class, AlSongMetadataTestCase.class,
		LyricUtilTestCase.class, Md5UtilTestCase.class })
public class AllTests {
}
