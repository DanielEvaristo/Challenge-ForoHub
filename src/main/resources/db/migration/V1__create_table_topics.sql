CREATE TABLE usuario (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  contrasena VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE curso (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  categoria VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE topico (
  id BIGINT NOT NULL AUTO_INCREMENT,
  titulo VARCHAR(200) NOT NULL,
  mensaje TEXT NOT NULL,
  fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  status VARCHAR(30) NOT NULL DEFAULT 'ABIERTO',
  autor_id BIGINT NOT NULL,
  curso_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_topico_autor FOREIGN KEY (autor_id) REFERENCES usuario (id),
  CONSTRAINT fk_topico_curso FOREIGN KEY (curso_id) REFERENCES curso (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE respuesta (
  id BIGINT NOT NULL AUTO_INCREMENT,
  mensaje TEXT NOT NULL,
  fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  solucion BOOLEAN DEFAULT FALSE,
  topico_id BIGINT NOT NULL,
  autor_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_respuesta_topico FOREIGN KEY (topico_id) REFERENCES topico (id),
  CONSTRAINT fk_respuesta_autor FOREIGN KEY (autor_id) REFERENCES usuario (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_topico_autor ON topico(autor_id);
CREATE INDEX idx_topico_curso ON topico(curso_id);
CREATE INDEX idx_respuesta_topico ON respuesta(topico_id);
