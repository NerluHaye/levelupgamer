![Imagen de WhatsApp 2025-10-20 a las 01 53 22_82a96099](https://github.com/user-attachments/assets/cde4492e-e0e3-43b4-9cce-0cffdf122bfc)


# LevelUp Gamer — Control de Gastos para Gamers 🎮

## 📱 Objetivo del proyecto

Como equipo, nuestro objetivo es desarrollar **una aplicación móvil funcional y completa**, que refleje un proceso de diseño y una implementación bien aplicado. 
Buscamos construir una app **modular, visualmente coherente y usable**, que integrara una **Arquitectura MVVM** robusta, **validaciones** claras, **persistencia remota** (Backend/PostgreSQL), acceso a **recursos nativos**, y la implementación de **Pruebas Unitarias** para garantizar la fiabilidad del código a largo plazo.

## 📱 Descripción

Nuestro proyecto consiste en el desarrollo de una **Aplicación móvil nativa para Android**, creada en **Kotlin con Jetpack Compose**, diseñada para la comunidad gamer para llevar un control detallado de sus compras y consumos. La aplicación ofrece una experiencia moderna y fluida, cuyo valor técnico se centra en una **Arquitectura MVVM** robusta, asegurando modularidad y mantenibilidad. Hemos implementado **persistencia remota** conectando la aplicación a un Backend (que utiliza **PostgreSQL**) para manejar la gestión de datos sensibles y la autenticación de usuarios. Finalmente, se integró una suite de **Pruebas Unitarias** que validan la lógica del negocio y la capa de datos, garantizando la confiabilidad del sistema ante fallos.

## 📱 Colaboradores 

- **Alexander Bello** – Logica de validación, manejo de navegación y desarrollo de Backend.  
- **Nicolás Jerez** – Diseño visual , gestión de estado y desarrollo de Recursos nativos.  
- **Abraham Neira** – Estructura modular (MVVM), monitoreo de la aplicacion y desarrollo de Pruebas unitarias.

## 📱 Funcionalidades del proyecto

### Interfaz y Navegación:
- **Pantalla principal:** Con acceso a las funciones principales.  
- **Formulario validado:** Para agregar gastos con retroalimentación visual (íconos y mensajes de error).  

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

### Pruebas unitarias: 
- **Cobertura de Capas:** Suite de **4 pruebas** implementadas en la Capa de Datos (`ProductRepository`) y la Capa de Lógica (`LoginViewModel`, `RegisterViewModel`, `ProductViewModel`).
- **Enfoque de Validación:** Las pruebas se centran en validar la **robustez** (manejo de fallos de red en el Repository), la **seguridad** (validación de entradas antes del registro) y la correcta **gestión de estados** de la UI.
- **Herramientas:** Se utilizan **JUnit**, **Mockito-Kotlin** para simular dependencias de la API, y **Kotlin Coroutines Test** para probar la lógica asíncrona.

## 📱 Estructura 

### Etapa 1 Planificación:
- Configuración inicial del proyecto en **Android Studio**.
- Creación del **Tablero Trello** con planificación por semanas. 
- Creación del **Repositorio GitHub** con commits iniciales y archivo README.md.

### Etapa 2 Diseño de Interfaz y Navegación:
- Creación de pantallas principales: `CartScreen.kt`, `ProductDetailScreen.kt`, `ProductListScreen.kt`.
- Validación visual de formularios con mensajes de error.  

### Etapa 3 Validación: 
- Implementación de validaciones desde el **ViewModel**, separando la lógica de la vista.
- Manejo de flujo entre ingreso, validación y almacenamiento de datos.

### Etapa 4 Persistencia Local y Arquitectura:
- Implementación del patrón **MVVM** para mantener la aplicación modular y mantenible.  
- Creación del `ProductRepository.kt` para manejar la comunicación entre ViewModel y base de datos.
- Integración de **Pruebas Unitarias:** Desarrollo de la suite de pruebas para el `ProductRepository` y los `ViewModels` (Login, Register, Product).
- Organización clara de carpetas (`data`, `model`, `repository`, `viewmodel`, `ui`, `util`) y para las pruebas (`test/repository/`, `test/viewmodel/`).  
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

## 📱 Pasos para Ejecutar el Proyecto

1. Clonar el repositorio desde GitHub:  
   ```bash
   git clone https://github.com/tuusuario/levelupgamer.git
2. Por la implementación de Ngrok debe ejecurtase primero el backend, luego Ngrok( escribir "ngrok http 8080" en la terminal ).
   
3. Abrir el proyecto en Android Studio.
   
4. Sincronizar las dependencias de Gradle.

5. Ejecutar el emulador o conectar un dispositivo físico.

6. Ejecutar la aplicación desde `MainActivity.kt`.
