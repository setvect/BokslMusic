package com.setvect.bokslmusic.play;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import javazoom.spi.PropertiesContainer;

/**
 * MP3 Player
 */
public class MP3Player {
	private File musicFile;

	public void setMusicFile(File music) {
		this.musicFile = music;
	}

	public void play() {
		try {
			PrintStream out = System.out;
			AudioInputStream in = AudioSystem.getAudioInputStream(musicFile);
			AudioInputStream din = null;
			if (in != null) {
				AudioFormat baseFormat = in.getFormat();
				if (out != null)
					out.println("Source Format : " + baseFormat.toString());
				AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
						baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2,
						baseFormat.getSampleRate(), false);
				if (out != null)
					out.println("Target Format : " + decodedFormat.toString());
				din = AudioSystem.getAudioInputStream(decodedFormat, in);
				if (din instanceof PropertiesContainer) {
					out.println("PropertiesContainer : OK");
				}
				else {
					out.println("Wrong PropertiesContainer instance");
				}
				rawplay(decodedFormat, din);
				in.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void rawplay(AudioFormat targetFormat, AudioInputStream din) throws LineUnavailableException,
			IOException {
		byte[] data = new byte[4096];
		SourceDataLine line = getLine(targetFormat);
		if (line != null) {
			// Start
			line.start();
			int nBytesRead = 0, nBytesWritten = 0;
			while (nBytesRead != -1) {
				nBytesRead = din.read(data, 0, data.length);
				if (nBytesRead != -1)
					nBytesWritten = line.write(data, 0, nBytesRead);
			}
			// Stop
			line.drain();
			line.stop();
			line.close();
			din.close();
		}
	}

	private static SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException {
		SourceDataLine res = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		res = (SourceDataLine) AudioSystem.getLine(info);
		res.open(audioFormat);
		return res;
	}

}
