class VideoTag
{
	int scene;
	int shoot;
	int take;
	int checksum;
	boolean bj;
	boolean jh;
	boolean pa;

	public int getScene()
	{
		return scene;
	}

	public void setScene(int scene)
	{
		this.scene = scene;
	}

	public int getShoot()
	{
		return shoot;
	}

	public void setShoot(int shoot)
	{
		this.shoot = shoot;
	}

	public int getTake()
	{
		return take;
	}

	public void setTake(int take)
	{
		this.take = take;
	}

	public int getChecksum()
	{
		return checksum;
	}

	public void setChecksum(int checksum)
	{
		this.checksum = checksum;
	}

	public boolean isBj()
	{
		return bj;
	}

	public void setBj(boolean bj)
	{
		this.bj = bj;
	}

	public boolean isJh()
	{
		return jh;
	}

	public void setJh(boolean jh)
	{
		this.jh = jh;
	}

	public boolean isPa()
	{
		return pa;
	}

	public void setPa(boolean pa)
	{
		this.pa = pa;
	}

	@Override
	public String toString()
	{
		return "VideoTag{" + "scene=" + scene + ", shoot=" + shoot + ", take=" + take + ", checksum=" + checksum + ", bj=" + bj + ", jh=" + jh + ", pa=" + pa + '}';
	}
}
