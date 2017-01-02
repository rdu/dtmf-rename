import java.util.ArrayList;
import java.util.List;

class Decoder
{
	public VideoTag decode(String data)
	{
//		System.out.println(data);
		String[] chars = data.split("\\*+");

		List<String> codeList = new ArrayList<String>();

		for (String c : chars)
		{
			if (c.length() > 0)
			{
				codeList.add(Character.toString(c.charAt(0)));
			}
		}

		if (codeList.size() == 9)
		{
			try
			{
				VideoTag vt = new VideoTag();
				vt.setScene(Integer.parseInt(codeList.get(0) + codeList.get(1)));
				vt.setShoot(Integer.parseInt(codeList.get(2) + codeList.get(3)));
				vt.setTake(Integer.parseInt(codeList.get(4) + codeList.get(5)));
				vt.setChecksum(Integer.parseInt(codeList.get(7) + codeList.get(8)));
				int actors = Integer.parseInt(codeList.get(6));
				vt.setPa((actors & 0b1) != 0);
				vt.setJh(((actors >> 1) & 0b1) != 0);
				vt.setBj(((actors >> 2) & 0b1) != 0);

				boolean start = vt.isBj() ^ vt.isJh() ^ vt.isPa();
				int sum = vt.getScene() + vt.getShoot() + vt.getTake();
				sum = sum << 1;
				sum = sum ^ (start ? 0 : 1);
				int cs = ((sum & 0xf) ^ ((sum & 0xf0) >> 8) ^ ((sum & 0xf00) >> 16) ^ ((sum & 0xf000) >> 24) ^ ((sum & 0xf0000) >> 32)) & 0xff;
				if (vt.getChecksum() == cs)
				{
					return vt;
				}
			}
			catch (Exception e)
			{
			}
		}
		if (data.length() > 1)
		{
			return decode(data.substring(1));
		}
		return null;
	}
}