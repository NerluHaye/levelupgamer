![Imagen de WhatsApp 2025-10-20 a las 01 53 22_82a96099](https://github.com/user-attachments/assets/cde4492e-e0e3-43b4-9cce-0cffdf122bfc)


# LevelUp Gamer — Control de Gastos para Gamers 🎮

## 📱 Descripción

Nuestro proyecto consiste en el desarrollo de una **Aplicación móvil para Android**, creada en **Kotlin con Jetpack Compose**, que permite a los gamers llevar un control de sus compras y consumos dentro del mundo del gaming. La Aplicación ofrece una experiencia moderna y fluida, combinando diseño visual, validaciones, almacenamiento local y acceso a funciones nativas del dispositivo.

## 📱 Colaboradores 

- **Alexander Bello** – Logica de validacion, navegación y Backend.  
- **Nicolás Jerez** – Diseño visual , gestión de estado y recursos nativos.  
- **Abraham Neira** – estructura modular (MVVM), monitoreo y Pruebas unitarias.

## 📱 Funcionalidades del proyecto

### Interfaz y Navegación:
- **Pantalla principal** con acceso a las funciones principales.  
- **Formulario validado** para agregar gastos con retroalimentación visual (íconos y mensajes de error).  

### Validaciones:
- Validaciones lógicas manejadas desde `ViewModel`, separadas de la UI.

### Persistencia Remota:
- Base de datos remota **PostGrade** con sql implementada con aws.  
- Estructura modular siguiendo el patrón **MVVM**.  
- Proyecto organizado con carpetas.
- Control de versiones en **GitHub** y planificación en **Trello**.

### Autenticación de usuarios:
- **Formulario** con validaciones visuales.
- **Registro e inicio de sesión** con verficación local de usuarios.

### Recursos nativos:
- **Lector de archivo** integrado para la comprobante de la compra pdf.
- **Gps** Para confirmar el lugar donde se debe enviar las compras.

## 📱 Estructura 

### Etapa 1 Planificación:
- Configuración inicial del proyecto en **Android Studio**.
- Creación del **tablero Trello** con planificación por semanas. 
- Creación del **repositorio GitHub** con commits iniciales y archivo README.md.

### Etapa 2 Diseño de Interfaz y Navegación:
- Creación de pantallas principales: `CartScreen.kt`, `ProductDetailScreen.kt`, `ProductListScreen.kt`.
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

### Etapa 6 Integracion de Backend y Base de datos:
- Conexion entre la app mobile y el backend que esta conectando con una base de datos 
- Comunicacion entre la base de datos para la validacion de productos y usuarios
### Etapa 7 Creacion del apk y firma 
- Finalizacion del proyecto, convirtiendolo en un apk y dando la firma de keystore

<img width="687" height="486" alt="Captura de pantalla 2025-11-17 052419" src="https://github.com/user-attachments/assets/33c8ff45-e0fd-4445-94be-0cd2664ad507" />

## 📱 Objetivo del proyecto

Como equipo, nuestro objetivo es desarrollar **una aplicación móvil funcional y completa**, que refleje un proceso de diseño y una implementación bien aplicado. 
Buscamos construir una app **modular, visualmente coherente y usable**, que integre diseño visual, validaciones, persistencia local, animaciones y acceso a recursos nativos.

## 📱 Pasos para Ejecutar el Proyecto

1. Clonar el repositorio desde GitHub:  
   ```bash
   git clone https://github.com/tuusuario/levelupgamer.git
2. Por la implementación de Ngrok debe ejecurtase primero el backend, luego Ngrok( escribir "ngrok http 8080" en la terminal ).
   
3. Abrir el proyecto en Android Studio.
   
4. Sincronizar las dependencias de Gradle.

5. Ejecutar el emulador o conectar un dispositivo físico.

6. Ejecutar la aplicación desde `MainActivity.kt`.
