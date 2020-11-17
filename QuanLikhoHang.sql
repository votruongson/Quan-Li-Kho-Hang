CREATE DATABASE QuanLiKhoHang
GO
USE QuanLiKhoHang
GO
CREATE TABLE dbo.NhanVien
    (
      MaNV INT IDENTITY(1, 1)
               NOT NULL ,
      HoTen NVARCHAR(50) NOT NULL ,
      TaiKhoan CHAR(10) NOT NULL ,
      MatKhau NVARCHAR(50) NOT NULL ,
      Gioitinh BIT NOT NULL ,
      Email NVARCHAR(50) NOT NULL ,
      DiaChi NVARCHAR(255) NOT NULL ,
      SoDienThoai VARCHAR(10) NOT NULL ,
      Hinh NVARCHAR(255) NOT NULL
                         DEFAULT 'Default.jpg' ,
      VaiTro BIT NOT NULL
                 DEFAULT 0 ,
      PRIMARY KEY ( MaNV )
    )
GO
CREATE TABLE dbo.KhachHang
    (
      MaKH INT IDENTITY(1, 1)
               NOT NULL ,
      TenKH NVARCHAR(50) NOT NULL ,
      GioiTinh BIT NOT NULL ,
      Email NVARCHAR(255) NOT NULL ,
      DiaChi NVARCHAR(255) NOT NULL ,
      SoDienThoai VARCHAR(10) NOT NULL ,
      Hinh NVARCHAR(255) NOT NULL
                         DEFAULT 'Default.jpg' ,
      PRIMARY KEY ( MaKH )
    )
GO
CREATE TABLE dbo.NhaCungCap
    (
      MaNCC INT IDENTITY(1, 1)
                NOT NULL ,
      TenCongTy NVARCHAR(255) NOT NULL ,
      DiaChi NVARCHAR(255) NOT NULL ,
      Email NVARCHAR(255) NOT NULL ,
      SoDienThoai VARCHAR(10) NOT NULL ,
      Hinh NVARCHAR(255) NOT NULL
                         DEFAULT 'Default.jpg' ,
      PRIMARY KEY ( MaNCC )
    )
GO
CREATE TABLE dbo.LoaiHang
    (
      MaLoai INT IDENTITY(1, 1)
                 NOT NULL ,
      TenLoai NVARCHAR(255) NOT NULL ,
      PRIMARY KEY ( MaLoai )
    )
GO
CREATE TABLE dbo.HangHoa
    (
      MaHH INT IDENTITY(1, 1)
               NOT NULL ,
      MaLoai INT NOT NULL ,
      TenHang NVARCHAR(255) NOT NULL ,
      DonGia DOUBLE PRECISION NOT NULL ,
      DonVi NVARCHAR(50) NOT NULL ,
      Hinh NVARCHAR(255) NOT NULL
                         DEFAULT 'Default.jpg' ,
      GhiChu NVARCHAR(255) NOT NULL ,
      PRIMARY KEY ( MaHH ) ,
      FOREIGN KEY ( MaLoai ) REFERENCES dbo.LoaiHang ( MaLoai )
    )
GO

CREATE TABLE dbo.HoaDonBanHang
    (
      MaHDBan INT IDENTITY(1, 1)
                  NOT NULL ,
      MaKH INT NOT NULL ,
      MaNV INT NOT NULL ,
      NgayBan DATE NOT NULL
                   DEFAULT GETDATE() ,
      TrangThai BIT NOT NULL ,
      GhiChu NVARCHAR(255) NOT NULL ,
      PRIMARY KEY ( MaHDBan ) ,
      FOREIGN KEY ( MaKH ) REFERENCES dbo.KhachHang ( MaKH ) ON UPDATE CASCADE
        ON DELETE CASCADE ,
      FOREIGN KEY ( MaNV ) REFERENCES dbo.NhanVien ( MaNV ) ON UPDATE CASCADE
        ON DELETE CASCADE
    )
GO
CREATE TABLE dbo.HoaDonMuaHang
    (
      MaHDMua INT IDENTITY(1, 1)
                  NOT NULL ,
      MaNCC INT NOT NULL ,
      MaNV INT NOT NULL ,
      NgayMua DATE NOT NULL
                   DEFAULT GETDATE() ,
      TrangThai BIT NOT NULL ,
      GhiChu NVARCHAR(255) NOT NULL ,
      PRIMARY KEY ( MaHDMua ) ,
      FOREIGN KEY ( MaNCC ) REFERENCES dbo.NhaCungCap ( MaNCC ) ON UPDATE CASCADE
        ON DELETE CASCADE ,
      FOREIGN KEY ( MaNV ) REFERENCES dbo.NhanVien ( MaNV ) ON UPDATE CASCADE
        ON DELETE CASCADE
    )
GO
CREATE TABLE dbo.HoaDonBanChiTiet
    (
      MaHDBan INT NOT NULL ,
      MaHH INT NOT NULL ,
      TenHang NVARCHAR(255) NOT NULL ,
      SoLuong INT NOT NULL ,
      DonVi NVARCHAR(50) NOT NULL ,
      NgayBan DATE NOT NULL
                   DEFAULT GETDATE() ,
      DonGia DOUBLE PRECISION NOT NULL ,
      GhiChu NVARCHAR(255) NOT NULL ,
      FOREIGN KEY ( MaHDBan ) REFERENCES dbo.HoaDonBanHang ( MaHDBan ) ,
      FOREIGN KEY ( MaHH ) REFERENCES dbo.HangHoa ( MaHH ) ,
      PRIMARY KEY ( MaHDBan, MaHH )
    )
GO
CREATE TABLE dbo.HoaDonMuaChiTiet
    (
      MaHDMua INT NOT NULL ,
      MaHH INT NOT NULL ,
      TenHang NVARCHAR(255) NOT NULL ,
      SoLuong INT NOT NULL ,
      DonVi NVARCHAR(50) NOT NULL ,
      NgayMua DATE NOT NULL
                   DEFAULT GETDATE() ,
      DonGia DOUBLE PRECISION NOT NULL ,
      GhiChu NVARCHAR(255) NOT NULL ,
      FOREIGN KEY ( MaHDMua ) REFERENCES dbo.HoaDonMuaHang ( MaHDMua ) ,
      FOREIGN KEY ( MaHH ) REFERENCES dbo.HangHoa ( MaHH ) ,
      PRIMARY KEY ( MaHDMua, MaHH )
    )
	GO
INSERT  INTO dbo.NhanVien
        ( HoTen, TaiKhoan, MatKhau, Gioitinh, Email, DiaChi, SoDienThoai, Hinh,
          VaiTro )
VALUES  ( N'Lê Thị Trúc Thư', -- HoTen - nvarchar(50)
          'ThuNV', -- TaiKhoan - char(10)
          N'123456', -- MatKhau - nvarchar(50)
          0, -- Gioitinh - bit
          N'Thultt@fpt.edu.vn', -- Email - nvarchar(50)
          N'Quận Bình Thạnh,TP.Hồ Chí Minh', -- DiaChi - nvarchar(255)
          0123456789, -- SoDienThoai - int
          N'Thu.jpg', -- Hinh - nvarchar(255)
          0  -- VaiTro - bit
          ),
        ( N'Ngô Hoàng Bảo Luân', -- HoTen - nvarchar(50)
          'LuanTP', -- TaiKhoan - char(10)
          N'123456', -- MatKhau - nvarchar(50)
          1, -- Gioitinh - bit
          N'Luannhb@fpt.edu.vn', -- Email - nvarchar(50)
          N'Quận Gò Vấp,TP.Hồ Chí Minh', -- DiaChi - nvarchar(255)
          0376551789, -- SoDienThoai - int
          N'Luan.jpg', -- Hinh - nvarchar(255)
          1  -- VaiTro - bit
          ),
        ( N'Nguyễn Thanh Hậu', -- HoTen - nvarchar(50)
          'HauNV', -- TaiKhoan - char(10)
          N'123456', -- MatKhau - nvarchar(50)
          1, -- Gioitinh - bit
          N'Haunt@fpt.edu.vn', -- Email - nvarchar(50)
          N'Quận 12,TP.Hồ Chí Minh', -- DiaChi - nvarchar(255)
          0379123456, -- SoDienThoai - int
          N'Hau.jpg', -- Hinh - nvarchar(255)
          0  -- VaiTro - bit
          ),
        ( N'Nguyễn Nhật Minh', -- HoTen - nvarchar(50)
          'MinhNN', -- TaiKhoan - char(10)
          N'1409', -- MatKhau - nvarchar(50)
          1, -- Gioitinh - bit
          N'Minhnn@fpt.edu.vn', -- Email - nvarchar(50)
          N'Quận Thủ Đức,TP.Hồ Chí Minh', -- DiaChi - nvarchar(255)
          0356557894, -- SoDienThoai - int
          N'Minh.png', -- Hinh - nvarchar(255)
          0  -- VaiTro - bit
          ),
        ( N'Võ Trường Sơn', -- HoTen - nvarchar(50)
          'SonNV', -- UserName - char(10)
          N'123456', -- MatKhau - nvarchar(50)
          1, -- Gioitinh - bit
          N'Sonvt@fpt.edu.vn', -- Email - nvarchar(50)
          N'Quận Tân Bình,TP.Hồ Chí Minh', -- DiaChi - nvarchar(255)
          0376234578, -- SoDienThoai - int
          N'Son.jpg', -- Hinh - nvarchar(255)
          0  -- VaiTro - bit
          )

INSERT  INTO dbo.KhachHang
        ( TenKH, GioiTinh, Email, DiaChi, SoDienThoai, Hinh )
VALUES  ( N'Nguyễn Anh Tuấn ', -- TenKH - nvarchar(50)
          1, -- GioiTinh - bit
          N'Tuanna@fpt.edu.vn', -- Email - nvarchar(255)
          N'Quận 9,TP.Hồ Chí Minh', -- DiaChi - nvarchar(255)
          0123456785, -- SoDienThoai - int
          N'Default.jpg'  -- Hinh - nvarchar(255)
          ),
        ( N'Nguyễn Anh Minh ', -- TenKH - nvarchar(50)
          1, -- GioiTinh - bit
          N'Minhna@fpt.edu.vn', -- Email - nvarchar(255)
          N'Quận 1,TP.Hồ Chí Minh', -- DiaChi - nvarchar(255)
          0366997485, -- SoDienThoai - int
          N'Default.jpg'  -- Hinh - nvarchar(255)
          ),
        ( N'Nguyễn Trúc Linh ', -- TenKH - nvarchar(50)
          0, -- GioiTinh - bit
          N'Linhnt@fpt.edu.vn', -- Email - nvarchar(255)
          N'Quận 2,TP.Hồ Chí Minh', -- DiaChi - nvarchar(255)
          0976321456, -- SoDienThoai - int
          N'Default.jpg'  -- Hinh - nvarchar(255)
          ),
        ( N'Trần Thúy Kiều', -- TenKH - nvarchar(50)
          0, -- GioiTinh - bit
          N'Kieutt@fpt.edu.vn', -- Email - nvarchar(255)
          N'Quận Tân Bình,TP.Hồ Chí Minh', -- DiaChi - nvarchar(255)
          0786441236, -- SoDienThoai - int
          N'Default.jpg'  -- Hinh - nvarchar(255)
          ),
        ( N'Nguyễn Văn Linh', -- TenKH - nvarchar(50)
          1, -- GioiTinh - bit
          N'Linhnv@fpt.edu.vn', -- Email - nvarchar(255)
          N'Quận Tân Phú,TP.Hồ Chí Minh', -- DiaChi - nvarchar(255)
          0785234612, -- SoDienThoai - int
          N'Default.jpg'  -- Hinh - nvarchar(255)
          )
	
INSERT  INTO dbo.NhaCungCap
        ( TenCongTy, DiaChi, Email, SoDienThoai, Hinh )
VALUES  ( N'Công Ty Sản Xuất Mô Hình 3D Naruto', -- TenCongTy - nvarchar(255)
          N'83A - Thành Thái - Quận 10 - TP.Hồ Chí Minh', -- DiaChi - nvarchar(255)
          N'PhuLam@gmail.com', -- Email - nvarchar(255)
          0736998512, -- SoDienThoai - int
          N'CT.jpg'  -- Hinh - nvarchar(255)
          ),
        ( N'Công Ty Sản Xuất Mô Hình 3D DC', -- TenCongTy - nvarchar(255)
          N'101A-Nguyễn Trãi-Quận 11-TP.Hồ Chí Minh', -- DiaChi - nvarchar(255)
          N'Shapper@gmail.com', -- Email - nvarchar(255)
          0746235467, -- SoDienThoai - int
          N'CT.jpg'  -- Hinh - nvarchar(255)
          ),
        ( N'Công Ty Sản Xuất Mô Hình 3D Onepiece', -- TenCongTy - nvarchar(255)
          N'97C-Trung Lương-Long An', -- DiaChi - nvarchar(255)
          N'TanPhat@gmail.com', -- Email - nvarchar(255)
          0756384756, -- SoDienThoai - int
          N'CT.jpg'  -- Hinh - nvarchar(255)
          ),
        ( N'Công Ty Sản Xuất Mô Hình 3D Marvel', -- TenCongTy - nvarchar(255)
          N'35D - Ngô Quyển - Vũng Tàu', -- DiaChi - nvarchar(255)
          N'Marvelm@gmail.com', -- Email - nvarchar(255)
          0786321478,  -- SoDienThoai - int
          N'CT.jpg'  -- Hinh - nvarchar(255)
          ),
        ( N'Công Ty Sản Xuất Mô Hình 3D Minh Chơn', -- TenCongTy - nvarchar(255)
          N'97F -Trúc Bạch - Hà Nội', -- DiaChi - nvarchar(255)
          N'Gate@gmail.com', -- Email - nvarchar(255)
          0865241789,  -- SoDienThoai - int
          N'CT.jpg'  -- Hinh - nvarchar(255)
          )

INSERT  INTO dbo.LoaiHang
        ( TenLoai )
VALUES  ( N'Marvel'  -- TenLoai - nvarchar(255)
          ),
        ( N'DC'  -- TenLoai - nvarchar(255)
          ),
        ( N'OnePiece'  -- TenLoai - nvarchar(255)
          ),
        ( N'Naruto'  -- TenLoai - nvarchar(255)
          ),
        ( N'7 Dragon Ball'  -- TenLoai - nvarchar(255)
          )
INSERT  INTO dbo.HangHoa
        ( MaLoai, TenHang, DonGia, DonVi, Hinh, GhiChu )
VALUES  ( 1, -- MaLoai - int
          N'Mô hình Hulk', -- TenHang - nvarchar(255)
          950000.0, -- DonGia - float
          N'Bộ', -- DonVi - nvarchar(50)
          N'Hulk.jpg', -- Hinh - nvarchar(255)
          N'Hàng nhập khẩu từ Hoa Kỳ'  -- GhiChu - nvarchar(255)
          ),
        ( 2, -- MaLoai - int
          N'Mô hình Naruto', -- TenHang - nvarchar(255)
          1500000.0, -- DonGia - float
          N'', -- DonVi - nvarchar(50)
          N'Naruto.jpg', -- Hinh - nvarchar(255)
          N'Hàng nhập khẩu từ Nhật Bản'  -- GhiChu - nvarchar(255)
          ),
        ( 3, -- MaLoai - int
          N'Mô hình Luffy', -- TenHang - nvarchar(255)
          2800000.0, -- DonGia - float
          N'Bộ', -- DonVi - nvarchar(50)
          N'Luffy.jpg', -- Hinh - nvarchar(255)
          N'Hàng nhập khẩu từ Nhật Bản'  -- GhiChu - nvarchar(255)
          ),
        ( 4, -- MaLoai - int
          N'Mô hình Batman', -- TenHang - nvarchar(255)
          1750000.0, -- DonGia - float
          N'Bộ', -- DonVi - nvarchar(50)
          N'Batman.jpg', -- Hinh - nvarchar(255)
          N'Hàng nhập khẩu từ Hoa Kỳ'  -- GhiChu - nvarchar(255)
          ),
        ( 5, -- MaLoai - int
          N'Mô hình Son Goku', -- TenHang - nvarchar(255)
          2000000.0, -- DonGia - float
          N'Bộ', -- DonVi - nvarchar(50)
          N'Son Goku.jpg', -- Hinh - nvarchar(255)
          N'Hàng nhập khẩu từ Nhật Bản'  -- GhiChu - nvarchar(255)
          )
INSERT  INTO dbo.HoaDonBanHang
        ( MaKH, MaNV, NgayBan, TrangThai, GhiChu )
VALUES  ( 1, -- MaKH - int
          1, -- MaNV - int
          '2019-05-05', -- NgayBan - date
          1, -- TrangThai - bit
          N'Đã xuất đủ hàng'  -- GhiChu - nvarchar(255)
          ),
        ( 2, -- MaKH - int
          2, -- MaNV - int
          '2019-05-05', -- NgayBan - date
          1, -- TrangThai - bit
          N'Đã xuất đủ hàng'  -- GhiChu - nvarchar(255)
          ),
        ( 3, -- MaKH - int
          3, -- MaNV - int
          '2019-05-05', -- NgayBan - date
          1, -- TrangThai - bit
          N'Đã xuất đủ hàng'  -- GhiChu - nvarchar(255)
          ),
        ( 4, -- MaKH - int
          4, -- MaNV - int
          '2019-05-05', -- NgayBan - date
          1, -- TrangThai - bit
          N'Đã xuất đủ hàng'  -- GhiChu - nvarchar(255)
          ),
        ( 5, -- MaKH - int
          5, -- MaNV - int
          '2019-05-05', -- NgayBan - date
          1, -- TrangThai - bit
          N'Đã xuất đủ hàng'  -- GhiChu - nvarchar(255)
          )
		
INSERT  INTO dbo.HoaDonMuaHang
        ( MaNCC, MaNV, NgayMua, TrangThai, GhiChu )
VALUES  ( 1, -- MaNCC - int
          1, -- MaNV - int
          '2019-04-05', -- NgayMua - date
          1, -- TrangThai - bit
          N'Đã nhập đủ hàng'  -- GhiChu - nvarchar(255)
          ),
        ( 2, -- MaNCC - int
          2, -- MaNV - int
          '2019-04-05', -- NgayMua - date
          1, -- TrangThai - bit
          N'Đã nhập đủ hàng'  -- GhiChu - nvarchar(255)
          ),
        ( 3, -- MaNCC - int
          3, -- MaNV - int
          '2019-04-05', -- NgayMua - date
          1, -- TrangThai - bit
          N'Đã nhập đủ hàng'  -- GhiChu - nvarchar(255)
          ),
        ( 4, -- MaNCC - int
          4, -- MaNV - int
          '2019-04-05', -- NgayMua - date
          1, -- TrangThai - bit
          N'Đã nhập đủ hàng'  -- GhiChu - nvarchar(255)
          ),
        ( 5, -- MaNCC - int
          5, -- MaNV - int
          '2019-04-05', -- NgayMua - date
          1, -- TrangThai - bit
          N'Đã nhập đủ hàng'  -- GhiChu - nvarchar(255)
          )

INSERT  INTO dbo.HoaDonBanChiTiet
        ( MaHDBan, MaHH, TenHang, SoLuong, DonVi, NgayBan, DonGia, GhiChu )
VALUES  ( 1, -- MaHDBan - int
          1, -- MaHH - int
          N'Mô hình Hulk', -- TenHang - nvarchar(255)
          20, -- SoLuong - int
          N'Bộ', -- DonVi - nvarchar(50)
          '2019-05-05', -- NgayBan - date
          1000000.0, -- DonGia - float
          N'Đã đủ hàng'  -- GhiChu - nvarchar(255)
          ),
        ( 2, -- MaHDBan - int
          2, -- MaHH - int
          N'Mô hình Naruto', -- TenHang - nvarchar(255)
          100, -- SoLuong - int
          N'Bộ', -- DonVi - nvarchar(50)
          '2019-05-05', -- NgayBan - date
          1750000.0, -- DonGia - float
          N'Đã đủ hàng '  -- GhiChu - nvarchar(255)
          ),
        ( 3, -- MaHDBan - int
          3, -- MaHH - int
          N'Mô hình Luffy', -- TenHang - nvarchar(255)
          150, -- SoLuong - int
          N'Bộ', -- DonVi - nvarchar(50)
          '2019-05-05', -- NgayBan - date
          3000000.0, -- DonGia - float
          N'Đã đủ hàng '  -- GhiChu - nvarchar(255)
          ),
        ( 4, -- MaHDBan - int
          4, -- MaHH - int
          N'Mô hình Batman', -- TenHang - nvarchar(255)
          300, -- SoLuong - int
          N'Bộ', -- DonVi - nvarchar(50)
          '2019-05-05', -- NgayBan - date
          2000000.0, -- DonGia - float
          N'Đã đủ hàng '  -- GhiChu - nvarchar(255)
          ),
        ( 5, -- MaHDBan - int
          5, -- MaHH - int
          N'Mô hình Son Goku', -- TenHang - nvarchar(255)
          300, -- SoLuong - int
          N'Bộ', -- DonVi - nvarchar(50)
          '2019-05-05', -- NgayBan - date
          2500000.0, -- DonGia - float
          N'Đã đủ hàng '  -- GhiChu - nvarchar(255)
          )

INSERT  INTO dbo.HoaDonMuaChiTiet
        ( MaHDMua, MaHH, TenHang, SoLuong, DonVi, NgayMua, DonGia, GhiChu )
VALUES  ( 1, -- MaHDMua - int
          1, -- MaHH - int
          N'Mô hình Hulk', -- TenHang - nvarchar(255)
          50, -- SoLuong - int
          N'Bộ', -- DonVi - nvarchar(50)
          '2019-04-05', -- NgayMua - date
          950000.0, -- DonGia - float
          N'Đã nhập đủ hàng'  -- GhiChu - nvarchar(255)
          ),
        ( 2, -- MaHDMua - int
          2, -- MaHH - int
          N'Mô hình Naruto', -- TenHang - nvarchar(255)
          100, -- SoLuong - int
          N'Bộ', -- DonVi - nvarchar(50)
          '2019-04-05', -- NgayMua - date
          1500000.0, -- DonGia - float
          N'Đã nhập đủ hàng'  -- GhiChu - nvarchar(255)
          ),
        ( 3, -- MaHDMua - int
          3, -- MaHH - int
          N'Mô hình Luffy', -- TenHang - nvarchar(255)
          220, -- SoLuong - int
          N'Bộ', -- DonVi - nvarchar(50)
          '2019-04-05', -- NgayMua - date
          2800000.0, -- DonGia - float
          N'Đã nhập đủ hàng'  -- GhiChu - nvarchar(255)
          ),
        ( 4, -- MaHDMua - int
          4, -- MaHH - int
          N'Mô hình Batman', -- TenHang - nvarchar(255)
          300, -- SoLuong - int
          N'Bộ', -- DonVi - nvarchar(50)
          '2019-04-05', -- NgayMua - date
          1750000.0, -- DonGia - float
          N'Đã nhập đủ hàng'  -- GhiChu - nvarchar(255)
          ),
        ( 5, -- MaHDMua - int
          5, -- MaHH - int
          N'Mô hình Son Goku', -- TenHang - nvarchar(255)
          300, -- SoLuong - int
          N'Bộ', -- DonVi - nvarchar(50)
          '2019-04-05', -- NgayMua - date
          2000000.0, -- DonGia - float
          N'Đã nhập đủ hàng'  -- GhiChu - nvarchar(255)
          )
	
	GO
CREATE PROC sp_ThongKeKhachHang
AS
    BEGIN
        SELECT 
                a.MaKH MaKhachHang,
				c.MaHH MaHangHoa,
				b.MaHDBan MaHDBan,
				c.SoLuong SoLuongBan,
				c.DonGia*c.SoLuong DoanhThu
				
        FROM    dbo.KhachHang a JOIN dbo.HoaDonBanHang b ON a.MaKH=b.MaKH JOIN dbo.HoaDonBanChiTiet c ON b.MaHDBan=c.MaHDBan
      		
    END
	GO
	CREATE PROC sp_ThongKeNhanVien
AS
    BEGIN
        SELECT 
                a.MaNV MaNhanVien,
				c.MaHH MaHangHoa,
				b.MaHDMua MaHDMua,
				c.SoLuong SoLuongMua,
				(c.DonGia*c.SoLuong) DoanhThu
				
        FROM     dbo.NhanVien a JOIN dbo.HoaDonMuaHang b ON a.MaNV=b.MaNV JOIN dbo.HoaDonMuaChiTiet c ON b.MaHDMua=c.MaHDMua
		
      
    END

GO
--EXEC sp_ThongKeNhanVien
GO
CREATE PROC sp_ThongKeDoanhThu 
AS
    BEGIN
        
		
		Select b.MaHH MaHangHoa ,
		sum(b.Soluong) SoLuongNhap,
		sum(a.Soluong) SoLuongBan,
		sum(b.Soluong)-sum(a.Soluong) SoLuongTonKho,
		sum(b.Soluong*b.DonGia)TongTienNhap,
		sum(a.SoLuong*a.DonGia)TongTienXuat,
		 sum(a.SoLuong*a.DonGia)-sum(b.Soluong*b.DonGia) TongTienChenhLech
from HoaDonBanChiTiet a join HoaDonMuaChiTiet b on a.MaHH = b.MaHH
group by b.MaHH;
    END
	GO
  --EXEC sp_ThongKeDoanhThu 