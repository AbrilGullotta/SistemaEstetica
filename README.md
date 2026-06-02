# Sistema de Gestión de Turnos — Centro Estético

Sistema de escritorio desarrollado en Java para la gestión de turnos de un centro estético. Permite la reserva, modificación y cancelación de turnos, gestión de clientes y profesionales, registro de señas y programación de recordatorios, reemplazando el uso de WhatsApp y agendas físicas por una solución digital centralizada.

---

## Tecnologías utilizadas

- Java SE 8
- MySQL / MariaDB
- JDBC
- BCrypt (jBCrypt) para encriptación de contraseñas
- Eclipse IDE
- phpMyAdmin

---

## Requisitos previos

- JDK 8 o superior
- MySQL o XAMPP con MariaDB activo
- Eclipse IDE
- Librería jBCrypt agregada al proyecto

---


## Instalación y configuración

### 1. Descomprimir el proyecto

Descomprimí el archivo `.zip` y abrí la carpeta `SistemaEstetica`.

### 2. Importar el proyecto en Eclipse

- Abrí Eclipse
- File → Import → Existing Projects into Workspace
- Seleccioná la carpeta del proyecto

### 3. Agregar la librería jBCrypt

- Descargá `jbcrypt-0.4.jar`
- Click derecho en el proyecto → Build Path → Add External Archives
- Seleccioná el jar

### 4. Crear la base de datos

- Abrí phpMyAdmin
- Creá una base de datos llamada `sistema_estetica`
- Importá el archivo `sistema_estetica.sql` incluido en el repositorio

### 5. Configurar la conexión

En `src/conexion/Conexion.java` verificá que los datos coincidan con tu entorno:

```java
private static final String URL = "jdbc:mysql://localhost:3306/sistema_estetica";
private static final String USER = "root";
private static final String PASSWORD = "";
```

### 6. Ejecutar

Corré la clase `src/main/Main.java`

---

## Credenciales de acceso

El sistema incluye un usuario administrador por defecto:

| Campo | Valor |
|-------|-------|
| Email | admin@test.com |
| Contraseña |admin123 |

---

## Estructura del proyecto

```
src/
├── conexion/
│   └── Conexion.java          — Conexión singleton a la base de datos
├── interfaz/
│   ├── MenuPrincipal.java     — Login y registro
│   ├── MenuAdministrador.java — Menú del administrador/recepcionista
│   ├── MenuCliente.java       — Menú del cliente
│   └── MenuProfesional.java   — Menú del profesional
├── modelo/
│   ├── Usuario.java
│   ├── Cliente.java
│   ├── Profesional.java
│   ├── Administrador.java
│   ├── Turno.java
│   ├── Servicio.java
│   ├── Disponibilidad.java
│   ├── Senia.java
│   └── Recordatorio.java
├── repository/
│   ├── UsuarioRepository.java
│   ├── TurnoRepository.java
│   ├── ServicioRepository.java
│   ├── DisponibilidadRepository.java
│   ├── SeniaRepository.java
│   └── RecordatorioRepository.java
├── servicio/
│   ├── UsuarioService.java
│   ├── TurnoService.java
│   ├── ServicioService.java
│   ├── SeniaService.java
│   └── RecordatorioService.java
├── util/
│   ├── Encriptador.java       — Hash y verificación con BCrypt
│   └── Validador.java         — Validaciones de campos
└── main/
    └── Main.java
```

---

## Funcionalidades por rol

### Administrador / Recepcionista
- Registrar, modificar y buscar clientes
- Registrar profesionales
- Registrar, modificar y gestionar turnos
- Registrar y gestionar servicios
- Registrar señas para confirmar turnos

### Cliente
- Registrarse e iniciar sesión
- Reservar, consultar y cancelar turnos
- Pagar seña para confirmar un turno
- Ver y modificar su perfil

### Profesional
- Iniciar sesión
- Cargar y ver su disponibilidad (días y horarios)
- Consultar sus turnos asignados

---

## Módulos implementados

| Módulo | Estado |
|--------|--------|
| Seguridad (login por rol) | Completo |
| Turnos | Completo |
| Clientes | Completo |
| Profesionales | Completo |
| Servicios | Completo |
| Señas | Completo |
| Recordatorios | Implementado (simulado en DB) |

---

## Base de datos

El archivo `sistema_estetica.sql` incluye la estructura completa de las tablas y datos de prueba:

- `usuario` — clientes, profesionales y administrador
- `turno` — reservas con estado (RESERVADO / CONFIRMADO / CANCELADO / COMPLETADO)
- `servicio` — servicios con precio y duración
- `disponibilidad` — días y horarios disponibles por profesional
- `senia` — señas asociadas a turnos
- `recordatorio` — recordatorios programados por turno

---

## Autores

Trabajo Práctico — Programación Avanzada
Abril Gullotta