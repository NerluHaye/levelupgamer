# LevelUp Gamer — Control de Gastos para Gamers 🎮

## 📱 Descripción

Nuestro proyecto consiste en el desarrollo de una **Aplicación móvil para Android**, creada en **Kotlin con Jetpack Compose**, que permite a los gamers llevar un control de sus compras y consumos dentro del mundo del gaming. La Aplicación ofrece una experiencia moderna y fluida, combinando diseño visual, validaciones, almacenamiento local y acceso a funciones nativas del dispositivo.

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

### Autenticación de usuarios:
- **Formulario** con validaciones visuales.
- **Registro e inicio de sesión** con verficación local de usuarios.

### Recursos nativos:
- **Cámara** integrada para tomar fotos de recibos o productos.
- **Notificaciones locales** para confirmar el agregado al carrito.

## 📱 Estructura 

### Etapa 1 Planificación:
- Configuración inicial del proyecto en **Android Studio**.
- Creación del **tablero Trello** con planificación por semanas. 
- Creación del **repositorio GitHub** con commits iniciales y archivo README.md.

### Etapa 2 Diseño de Interfaz y Navegación:
- Creación de pantallas principales: `CartScreen.kt`, `ProductDetailScreen.kt`, `ProductListScreen.kt` .
- Validación visual de formularios con mensajes de error.  

### Etapa 3 Validación: 
- Implementación de validaciones desde el **ViewModel**, separando la lógica de la vista.
- Manejo de flujo entre ingreso, validación y almacenamiento de datos.

### Etapa 4 Persistencia Local y Arquitectura:
- Implementación del patrón **MVVM** para mantener la aplicación modular y mantenible.  
- Creación del `ProductRepository.kt` para manejar la comunicación entre ViewModel y base de datos.  
- Organización clara de carpetas (`data`, `model`, `repository`, `viewmodel`, `ui`, `util`).  
- Seguimiento colaborativo en **GitHub y Trello**.

### Etapa 5 Recursos Nativos y Animaciones:
- Pruebas completas de navegación, persistencia y animaciones.  
- Validación del funcionamiento de los recursos nativos en emulador o dispositivo real.

## 📱 Obejtivo del proyecto

Como equipo, nuestro objetivo es desarrollar **una aplicación móvil funcional y completa**, que refleje un proceso de diseño y una implementación bien aplicado. 
Buscamos construir una app **modular, visualmente coherente y usable**, que integre diseño visual, validaciones, persistencia local, animaciones y acceso a recursos nativos.

## 📱 Pasos para Ejecutar el Proyecto

1. Clonar el repositorio desde GitHub:  
   ```bash
   git clone https://github.com/tuusuario/levelupgamer.git
   
2. Abrir el proyecto en Android Studio.

3. Sincronizar las dependencias de Gradle.

4. Ejecutar el emulador o conectar un dispositivo físico.

5. Ejecutar la aplicación desde `MainActivity.kt`.
