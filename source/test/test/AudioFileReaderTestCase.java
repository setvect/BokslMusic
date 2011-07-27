package test;

import java.util.List;

import javax.sound.sampled.spi.AudioFileReader;

import org.junit.Test;

import com.sun.media.sound.JDK13Services;

/**
 * 지원하는 오디오 포멧
 */
public class AudioFileReaderTestCase {
	@Test
	public void test() {
		@SuppressWarnings("unchecked")
		List<AudioFileReader> list = JDK13Services.getProviders(AudioFileReader.class);
		for (AudioFileReader o : list) {
			System.out.println(o);
		}
	}
}
