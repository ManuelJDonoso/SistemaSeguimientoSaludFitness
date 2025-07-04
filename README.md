# 🔷 Sistema de Seguimiento de Salud y Fitness

## 📌 Descripción General

Aplicación de escritorio desarrollada en Java (Swing/JavaFX), orientada a nutriólogos, entrenadores personales y profesionales de la salud. Permite llevar un control detallado de los clientes y sus progresos a través de métricas físicas y hábitos diarios.

---

## 🚀 Funcionalidades Principales

### ✅ Autenticación de Usuarios
- Login y registro con cifrado seguro.
- Hashing de contraseñas usando **BCrypt**.
- Mensajes de error específicos (usuario inexistente, contraseña incorrecta).

### 👥 Gestión de Clientes
- CRUD completo (crear, leer, actualizar, eliminar).
- Búsqueda rápida por nombre.
- Historial individual de cada cliente.

### 📊 Registro de Métricas de Salud
- Peso, altura, IMC (calculado automáticamente), circunferencia de cintura.
- Registro por fecha con historial progresivo.
- Validación de datos (ej: altura > 0).
- Visualización de progreso mediante **JFreeChart** o **JavaFX Charts**.

### 🗓 Seguimiento Diario
- Registro de hábitos diarios:
  - Número de comidas.
  - Litros de agua consumida.
  - Ejercicios realizados (nombre + duración).
- Selección de fecha con calendario integrado.


### 📑 Reportes
- Exportación de métricas a **CSV** o **PDF** mediante **iTextPDF**.
- Ideal para entregar informes al cliente.

---

## 🔧 Tecnologías Utilizadas

- **Lenguaje:** Java 8+ (Swing o JavaFX)
- **Base de Datos:** SQLite
- **Librerías:**
  - [BCrypt](https://github.com/patrickfav/bcrypt) — para cifrado de contraseñas.
  - [JFreeChart](https://www.jfree.org/jfreechart/) o [JavaFX Charts] — para gráficas de evolución.
  - [Lombok](https://projectlombok.org/) *(opcional)* — para reducir el boilerplate.
  - [iTextPDF](https://itextpdf.com/) — para generar reportes PDF.

---

## 🌱 GitFlow

Estructura de ramas usada en el proyecto:

- `main` — Rama estable para producción.
- `develop` — Última versión en desarrollo.
- `feature/auth` — Login/Registro de usuarios.
- `feature/client-crud` — Gestión de clientes.
- `feature/health-metrics` — Registro de métricas (IMC, cintura...).
- `feature/daily-tracking` — Seguimiento diario (agua, comida, ejercicios).

---

## 🌟 Habilidades y Conocimientos Adquiridos

- 🔐 **Seguridad:** Cifrado de contraseñas, validación de entradas, protección contra SQL Injection.
- 🗃 **Persistencia de Datos:** Uso eficaz de SQLite, triggers para cálculos automáticos.
- 🧑‍💻 **UI/UX:** Diseño intuitivo y validado de formularios.
- 📈 **Análisis de Datos:** Visualización gráfica del progreso del cliente.

---

## 📸 Capturas de Pantalla *(Próximamente)*



---

## 🧠 Contribuciones

¡Se aceptan sugerencias, mejoras y reportes de errores!  
Puedes abrir un Issue o hacer un Pull Request siguiendo el flujo de GitFlow.

---

## 📄 Licencia

Este proyecto está bajo la licencia **MIT**.  
Consulta el archivo `LICENSE` para más detalles.

---
