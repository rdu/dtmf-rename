import com.tino1b2be.audio.AudioFileException;
import com.tino1b2be.dtmfdecoder.DTMFDecoderException;
import com.tino1b2be.dtmfdecoder.DTMFUtil;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Main
{
	public static void main(String[] args) throws DTMFDecoderException, IOException, AudioFileException, InterruptedException
	{
		String dirname = ".";
		if (args != null && args.length > 0)
		{
			dirname = args[0];
		}
		File dir = new File(dirname);
		List<File> files = new ArrayList<>();
		for (File l : dir.listFiles())
		{
			String type = Files.probeContentType(l.toPath());
			if (type.startsWith("video") || type.startsWith("audio"))
			{
				files.add(l);
			}
		}

		for (File file : files)
		{
			processFile(file);
		}
	}

	private static void processFile(File file) throws IOException, InterruptedException, DTMFDecoderException, AudioFileException
	{
		File f = File.createTempFile("audio", ".wav");
		String waveName = f.toPath().toString();
		f.delete();

		Process p = Runtime.getRuntime().exec(new String[]{"ffmpeg", "-i", file.toPath().toString(), "-ac", "1", waveName});
		p.waitFor();

		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));

		String line = "";
		while ((line = reader.readLine()) != null)
		{
		}

		File testFile = new File(waveName);
		if (testFile.exists())
		{
			DTMFUtil dtmf = new DTMFUtil(waveName);
			dtmf.decode();
			String channel = dtmf.getDecoded()[0];
			Decoder d = new Decoder();
			VideoTag tag = d.decode(channel);
			if (tag != null)
			{
				StringBuilder newName = new StringBuilder();
				newName.append("Szene_");
				newName.append(tag.getScene());
				newName.append("_Aufnahme_");
				newName.append(tag.getShoot());
				newName.append("_");
				if (tag.isBj()) newName.append("BJ_");
				if (tag.isBj()) newName.append("JH_");
				if (tag.isBj()) newName.append("PA_");
				newName.append("#");
				newName.append(tag.getTake());
				newName.append(".");
				newName.append(FilenameUtils.getExtension(file.toPath().toString()));
				System.out.println("rename: " + file.getName() + " to " + newName.toString());
				file.renameTo(new File(file.getParent() + "/" + newName));

			}
			else
			{
				System.out.println("no tag found for: " + file.getName());
			}
		}
		else
		{
			System.out.println("cannot read file: " + file.getName());
		}
		File fd = new File(waveName);
		fd.delete();
	}
}