-- do_an_tot_nghiep.can_bo definition

CREATE TABLE `can_bo` (
  `can_bo_id` binary(255) NOT NULL,
  `co_so_id` binary(255) DEFAULT NULL,
  `email_fe` varchar(255) NOT NULL,
  `email_fpt` varchar(255) NOT NULL,
  `so_dien_thoai` varchar(255) NOT NULL,
  `ten_can_bo` varchar(255) NOT NULL,
  `ten_tai_khoan` varchar(255) NOT NULL,
  `vai_tro_id` binary(255) DEFAULT NULL,
  PRIMARY KEY (`can_bo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- do_an_tot_nghiep.chuyen_nganh definition

CREATE TABLE `chuyen_nganh` (
  `chuyen_nganh_id` binary(255) NOT NULL,
  `chuyen_nganh_me_id` varchar(255) NOT NULL,
  `co_so_id` binary(255) NOT NULL,
  `ten_chuyen_nganh` varchar(255) NOT NULL,
  PRIMARY KEY (`chuyen_nganh_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- do_an_tot_nghiep.chuyen_nganh_giang_vien definition

CREATE TABLE `chuyen_nganh_giang_vien` (
  `chuyen_nganh_id` binary(255) NOT NULL,
  `giang_vien_id` binary(255) NOT NULL,
  PRIMARY KEY (`chuyen_nganh_id`,`giang_vien_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- do_an_tot_nghiep.co_so definition

CREATE TABLE `co_so` (
  `co_so_id` binary(255) NOT NULL,
  `ten_co_so` varchar(255) NOT NULL,
  PRIMARY KEY (`co_so_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- do_an_tot_nghiep.dot_dang_ky definition

CREATE TABLE `dot_dang_ky` (
  `dot_dang_ky_id` binary(255) NOT NULL,
  `co_so_id` binary(255) NOT NULL,
  `hoc_ky_id` binary(255) NOT NULL,
  `ngay_bat_dau` datetime(6) NOT NULL,
  `ngay_ket_thuc` datetime(6) NOT NULL,
  `ten_dot_dang_ky` varchar(255) NOT NULL,
  PRIMARY KEY (`dot_dang_ky_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- do_an_tot_nghiep.giang_vien_huong_dan definition

CREATE TABLE `giang_vien_huong_dan` (
  `giang_vien_id` binary(255) NOT NULL,
  `co_so_id` binary(255) NOT NULL,
  `dot_dang_ky_id` binary(255) NOT NULL,
  `email_fe` varchar(255) NOT NULL,
  `email_fpt` varchar(255) NOT NULL,
  `so_dien_thoai` varchar(255) NOT NULL,
  `so_nhom_huong_dan_toi_da` smallint NOT NULL,
  `ten_gvhd` varchar(255) NOT NULL,
  `ten_tai_khoan` varchar(255) NOT NULL,
  PRIMARY KEY (`giang_vien_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- do_an_tot_nghiep.hoc_ky definition

CREATE TABLE `hoc_ky` (
  `hoc_ky_id` binary(255) NOT NULL,
  `ten_hoc_ky` varchar(255) NOT NULL,
  PRIMARY KEY (`hoc_ky_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- do_an_tot_nghiep.nhom_datn definition

CREATE TABLE `nhom_datn` (
  `nhom_id` binary(255) NOT NULL,
  `co_so_id` binary(255) NOT NULL,
  `dot_dang_ky_id` binary(255) NOT NULL,
  `giang_vien_id` binary(255) DEFAULT NULL,
  `mat_khau` varchar(255) DEFAULT NULL,
  `mo_ta` varchar(255) DEFAULT NULL,
  `ten_de_tai` varchar(255) DEFAULT NULL,
  `ten_nhom` varchar(255) DEFAULT NULL,
  `trang_thai` int NOT NULL,
  `truong_nhom_id` binary(255) NOT NULL,
  PRIMARY KEY (`nhom_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- do_an_tot_nghiep.sinh_vien definition

CREATE TABLE `sinh_vien` (
  `sinh_vien_id` binary(255) NOT NULL,
  `chuyen_nganh_id` binary(255) NOT NULL,
  `co_so_id` binary(255) NOT NULL,
  `dot_dang_ky_id` binary(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `khoa` varchar(255) NOT NULL,
  `ma_sinh_vien` varchar(255) NOT NULL,
  `nhom_id` binary(255) DEFAULT NULL,
  `so_dien_thoai` varchar(255) NOT NULL,
  `ten_sinh_vien` varchar(255) NOT NULL,
  `trang_thai` int DEFAULT NULL,
  PRIMARY KEY (`sinh_vien_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- do_an_tot_nghiep.vai_tro definition

CREATE TABLE `vai_tro` (
  `vai_tro_id` binary(255) NOT NULL,
  `ten_vai_tro` varchar(255) NOT NULL,
  PRIMARY KEY (`vai_tro_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;