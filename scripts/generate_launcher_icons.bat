@echo off
REM Script para generar los mipmaps de launcher desde una imagen fuente usando ImageMagick
REM Requiere: ImageMagick instalado y accesible como "magick" en PATH
REM Uso: copia tu imagen (ideal 1024x1024 PNG transparente) a la carpeta scripts y renómbrala a levelup.png
REM Luego ejecuta este script: scripts\generate_launcher_icons.bat

if not exist "%~dp0levelup.png" (
    echo ERROR: No se encontró levelup.png en la carpeta %~dp0
    echo Copia tu imagen en %~dp0 y renombrala a levelup.png (formato PNG preferido, tamaño >=1024x1024)
    pause
    exit /b 1
)

set SRC=%~dp0levelup.png
set RES_DIR=%~dp0..\app\src\main\res

echo Generando mipmaps desde %SRC% hacia %RES_DIR% ...

rem Crear carpetas si no existen
for %%d in (mipmap-mdpi mipmap-hdpi mipmap-xhdpi mipmap-xxhdpi mipmap-xxxhdpi mipmap-anydpi-v26 drawable) do (
    if not exist "%RES_DIR%\%%d" mkdir "%RES_DIR%\%%d"
)

rem Generar legacy launcher icons
magick "%SRC%" -resize 48x48 "%RES_DIR%\mipmap-mdpi\ic_launcher.png"
magick "%SRC%" -resize 72x72 "%RES_DIR%\mipmap-hdpi\ic_launcher.png"
magick "%SRC%" -resize 96x96 "%RES_DIR%\mipmap-xhdpi\ic_launcher.png"
magick "%SRC%" -resize 144x144 "%RES_DIR%\mipmap-xxhdpi\ic_launcher.png"
magick "%SRC%" -resize 192x192 "%RES_DIR%\mipmap-xxxhdpi\ic_launcher.png"

rem Generar foreground para adaptive icon (alta resolución)
magick "%SRC%" -resize 512x512 "%RES_DIR%\mipmap-anydpi-v26\ic_launcher_foreground.png"

rem Copiar foreground generado a drawable para que ic_launcher.xml lo referencie como @drawable/ic_launcher_foreground
copy /Y "%RES_DIR%\mipmap-anydpi-v26\ic_launcher_foreground.png" "%RES_DIR%\drawable\ic_launcher_foreground.png" >nul

rem (opcional) generar una versión monocroma simple si se desea
magick "%SRC%" -colorspace Gray -resize 108x108 "%RES_DIR%\drawable\ic_launcher_monochrome.png"


echo Hecho. Limpia y rebuild en Android Studio para ver los cambios.
pause
