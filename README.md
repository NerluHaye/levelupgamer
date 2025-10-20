# LevelUp Gamer — Control de Gastos para Gamers 🎮

## 📱 Descripción

Nuestro proyecto consiste en el desarrollo de una **aplicación móvil para Android**, creada en **Kotlin con Jetpack Compose**, cuyo objetivo es **registrar, visualizar y gestionar gastos personales de un gamer**. La aplicación busca ofrecer una experiencia fluida y moderna, integrando **principios de diseño visual, validaciones lógicas, persistencia local y acceso a recursos nativos del dispositivo**.

## 📱 Colaboradores 

- **Alexander Bello** – Diseño visual, navegación y animaciones funcionales.  
- **Nicolás Jerez** – Lógica de validaciones, gestión de estado y recursos nativos.  
- **Abraham Neira** – Persistencia local, estructura modular (MVVM) y monitoreo.

## 📱 Funcionalidades del proyecto

### Interfaz y Navegación:
-**Pantalla principal** con acceso a las funciones principales.  
- **Formulario validado** para agregar gastos con retroalimentación visual (íconos y mensajes de error).  

### Validaciones:
- Validaciones lógicas manejadas desde `ViewModel`, separadas de la UI.

### Persistencia Local:
- Base de datos local implementada con **Room** (DAO, Entity y Repository).  
- Estructura modular siguiendo el patrón **MVVM**.  
- Proyecto organizado con carpetas.
- Control de versiones en **GitHub** y planificación en **Trello**.

### Recursos nativos:
- **Cámara** integrada para tomar fotos de recibos o productos.
- **Notificaciones locales** para verificar el agregado al carrito.

## 📱 Estructura 

### Etapa 1 Planificación:
- Configuración inicial del proyecto en **Android Studio**.
- Creación del **tablero Trello** con planificación por semanas. 
- Creación del **repositorio GitHub** con commits iniciales y archivo README.md.

### Etapa 2 Diseño de Interfaz y Navegación:
- Creación de pantallas principales: `PantallaPrincipal.kt`.
- Validación visual de formularios con mensajes de error y íconos de retroalimentación.  

### Etapa 3 Validación: 
- Implementación de validaciones desde el **ViewModel**, separando la lógica de la vista.
- Manejo de flujo entre ingreso, validación y almacenamiento de datos.

### Etapa 4 Persistencia Local y Arquitectura:
- Implementación del patrón **MVVM** para mantener la aplicación modular y mantenible.  
- Creación del `ExpenseRepository.kt` para manejar la comunicación entre ViewModel y base de datos.  
- Organización clara de carpetas (`model`, `repository`, `viewmodel`, `ui`).  
- Seguimiento colaborativo en **GitHub y Trello**.

### Etapa 5 Recursos Nativos y Animaciones:
- Pruebas completas de navegación, persistencia y animaciones.  
- Validación del funcionamiento de los recursos nativos en emulador o dispositivo real.

## 📱 Obejtivo del proyecto

Como equipo, nuestro objetivo es desarrollar **una aplicación móvil funcional y completa**, que refleje un proceso de diseño y programación coherente con los aprendizajes de la asignatura.  
Buscamos construir una app **modular, visualmente coherente y usable**.

## 📱 Pasos para Ejecutar el Proyecto

1. Clonar el repositorio desde GitHub:  
   ```bash
   git clone https://github.com/tuusuario/levelupgamer.git
