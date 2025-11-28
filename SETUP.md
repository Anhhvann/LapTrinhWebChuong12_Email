# Email List Application - Setup Guide

## 🔐 Configuration

### Environment Variables

Ứng dụng sử dụng environment variables để lưu trữ thông tin nhạy cảm (credentials). 

**Các biến cần thiết:**

- `DB_URL` - Database connection URL
- `DB_USER` - Database username
- `DB_PASSWORD` - Database password

### Setup Local

1. **Tạo file `.env`** trong thư mục project:

```bash
DB_URL=jdbc:mysql://your-host:port/database?useSSL=true&requireSSL=true&serverTimezone=UTC
DB_USER=your_username
DB_PASSWORD=your_password
```

2. **Cấu hình Tomcat** để load environment variables:

**Trên Windows:**
- Copy file `setenv.bat` từ project vào `$CATALINA_HOME/bin/` 
- Hoặc set environment variables trực tiếp trong System Environment

**Trên Linux/Mac:**
```bash
export DB_URL="jdbc:mysql://..."
export DB_USER="username"
export DB_PASSWORD="password"
```

3. **Rebuild và Deploy:**
```bash
mvn clean package
```

4. **Restart Tomcat** để load biến môi trường mới

### Setup Production (GitHub)

1. **`.env` không được commit** (đã thêm vào `.gitignore`)
2. **Trên production server**, set environment variables trước khi chạy Tomcat:
   - Hoặc cấu hình trong Tomcat
   - Hoặc sử dụng Docker/Kubernetes secrets

### Migration từ JPA sang JDBC

Ứng dụng đã được migrate từ JPA/Hibernate sang JDBC:

- `User.java` - POJO class (không còn @Entity)
- `UserDB.java` - DAO sử dụng PreparedStatement
- `DBUtil.java` - Connection pool sử dụng C3P0

### Database

Bảng `users` cần có cấu trúc:

```sql
CREATE TABLE users (
    userId BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL
);
```

## 📝 Ghi chú quan trọng

⚠️ **KHÔNG commit file `.env` lên GitHub** - nó chứa credentials

✅ **DO commit:**
- Source code
- `.gitignore`
- `pom.xml`
- `setenv.bat` (example)

❌ **DON'T commit:**
- `.env` (credentials)
- `target/` (build output)
- `.idea/` (IDE files)
