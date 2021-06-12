package QLBH;

public class Loai
{
	@Override
	public String toString()
	{
		return tenloai;
	}
	public String getMaloai() {
		return maloai;
	}
	public void setMaloai(String maloai) {
		this.maloai = maloai;
	}
	public String getTenloai() {
		return tenloai;
	}
	public void setTenloai(String tenloai) {
		this.tenloai = tenloai;
	}
	public String getMahangsx() {
		return mahangsx;
	}
	public void setMahangsx(String mahangsx) {
		this.mahangsx = mahangsx;
	}
	private String maloai;
	private String tenloai;
	private String mahangsx;
	
}
