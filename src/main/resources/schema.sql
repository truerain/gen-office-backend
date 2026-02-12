CREATE TABLE IF NOT EXISTS "user" (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
    emp_no TEXT NOT NULL UNIQUE,
    emp_name TEXT NOT NULL,
    emp_name_eng TEXT NOT NULL,
    password TEXT NOT NULL,
    email TEXT,
    org_id TEXT NOT NULL,
    org_name TEXT,
    title_cd TEXT ,
    title_name TEXT,
    work_tel TEXT,
    mobile_tel TEXT,
    lang_cd TEXT NOT NULL,
    attribute1 TEXT,
    attribute2 TEXT,
    attribute3 TEXT,
    attribute4 TEXT,
    attribute5 TEXT,
    attribute6 TEXT,
    attribute7 TEXT,
    attribute8 TEXT,
    attribute9 TEXT,
    attribute10 TEXT,
    created_by TEXT ,
    creation_date TEXT DEFAULT CURRENT_TIMESTAMP,
    last_updated_by TEXT,
    last_updated_date TEXT DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tb_cm_menu(
    menu_id INT NOT NULL PRIMARY KEY,
    menu_name TEXT NOT NULL,
    menu_name_eng TEXT NOT NULL,
    menu_desc TEXT,
    menu_desc_eng TEXT,
    menu_level INT NOT NULL,
    menu_icon TEXT,
    exec_component TEXT,
    parent_menu_id INT,
    display_yn TEXT CHECK (display_yn IN ('Y','N')),
    use_yn TEXT CHECK (use_yn IN ('Y','N')),
    sort_order INT,
    attribute1 TEXT,
    attribute2 TEXT,
    attribute3 TEXT,
    attribute4 TEXT,
    attribute5 TEXT,
    attribute6 TEXT,
    attribute7 TEXT,
    attribute8 TEXT,
    attribute9 TEXT,
    attribute10 TEXT,
    creation_date TEXT DEFAULT CURRENT_TIMESTAMP,
    created_by TEXT,
    last_updated_date TEXT DEFAULT CURRENT_TIMESTAMP,
    last_updated_by TEXT
);

CREATE TABLE IF NOT EXISTS "role" (
    role_id INTEGER PRIMARY KEY AUTOINCREMENT,
    role_cd TEXT NOT NULL UNIQUE,
    role_name TEXT NOT NULL,
    role_name_eng TEXT NOT NULL,
    role_desc TEXT,
    sort_order INTEGER,
    use_yn TEXT CHECK (use_yn IN ('Y','N')),
    attribute1 TEXT,
    attribute2 TEXT,
    attribute3 TEXT,
    attribute4 TEXT,
    attribute5 TEXT,
    attribute6 TEXT,
    attribute7 TEXT,
    attribute8 TEXT,
    attribute9 TEXT,
    attribute10 TEXT,
    creation_date TEXT DEFAULT CURRENT_TIMESTAMP,
    created_by TEXT,
    last_updated_date TEXT DEFAULT CURRENT_TIMESTAMP,
    last_updated_by TEXT
);

CREATE TABLE IF NOT EXISTS "role_menu" (
    role_id INTEGER NOT NULL,
    menu_id INTEGER NOT NULL,
    use_yn TEXT CHECK (use_yn IN ('Y','N')),
    attribute1 TEXT,
    attribute2 TEXT,
    attribute3 TEXT,
    attribute4 TEXT,
    attribute5 TEXT,
    attribute6 TEXT,
    attribute7 TEXT,
    attribute8 TEXT,
    attribute9 TEXT,
    attribute10 TEXT,
    creation_date TEXT DEFAULT CURRENT_TIMESTAMP,
    created_by TEXT,
    last_updated_date TEXT DEFAULT CURRENT_TIMESTAMP,
    last_updated_by TEXT,
    PRIMARY KEY (role_id, menu_id),
    FOREIGN KEY (role_id) REFERENCES "role"(role_id),
    FOREIGN KEY (menu_id) REFERENCES "tb_cm_menu"(menu_id)
);
CREATE INDEX IF NOT EXISTS idx_role_menu_role_id ON role_menu (role_id);
CREATE INDEX IF NOT EXISTS idx_role_menu_menu_id ON role_menu (menu_id);

CREATE TABLE IF NOT EXISTS "user_role" (
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    use_yn TEXT CHECK (use_yn IN ('Y','N')),
    attribute1 TEXT,
    attribute2 TEXT,
    attribute3 TEXT,
    attribute4 TEXT,
    attribute5 TEXT,
    attribute6 TEXT,
    attribute7 TEXT,
    attribute8 TEXT,
    attribute9 TEXT,
    attribute10 TEXT,
    creation_date TEXT DEFAULT CURRENT_TIMESTAMP,
    created_by TEXT,
    last_updated_date TEXT DEFAULT CURRENT_TIMESTAMP,
    last_updated_by TEXT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES "user"(user_id),
    FOREIGN KEY (role_id) REFERENCES "role"(role_id)
);

CREATE INDEX IF NOT EXISTS idx_user_role_user_id
    ON user_role (user_id);

CREATE INDEX IF NOT EXISTS idx_user_role_role_id
    ON user_role (role_id);

CREATE TABLE IF NOT EXISTS "tb_cm_message" (
    message_cd TEXT NOT NULL,
    lang_cd TEXT NOT NULL,
    namespace TEXT NOT NULL,
    message_txt TEXT NOT NULL,
    attribute1 TEXT,
    attribute2 TEXT,
    attribute3 TEXT,
    attribute4 TEXT,
    attribute5 TEXT,
    attribute6 TEXT,
    attribute7 TEXT,
    attribute8 TEXT,
    attribute9 TEXT,
    attribute10 TEXT,
    created_by TEXT,
    creation_date TEXT DEFAULT CURRENT_TIMESTAMP,
    last_updated_by TEXT,
    last_updated_date TEXT DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (message_cd, lang_cd, namespace)
);

CREATE INDEX IF NOT EXISTS idx_tb_cm_message_ns_cd_lang
    ON tb_cm_message (namespace, message_cd, lang_cd);

CREATE INDEX IF NOT EXISTS idx_tb_cm_message_lang
    ON tb_cm_message (lang_cd);
