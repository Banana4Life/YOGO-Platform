##{{NSIS_PLUS_BEGIN_ADDITIONAL_FILES}}##
## \Users\aa\Desktop\#YOGO Platform\yogo-platform.jar
## \Users\aa\Desktop\#YOGO Platform\run.cmd
## \Users\aa\Desktop\#YOGO Platform\highscore.dat
## \Users\aa\Desktop\#YOGO Platform\res
## \Users\aa\Desktop\#YOGO Platform\natives
##{{NSIS_PLUS_END_ADDITIONAL_FILES}}##

##{{NSIS_PLUS_BEGIN_TODO}}##
#F2
#00Zu installierende Dateien per Drag&Drop hinzuf�gen
#A01
#00
##{{NSIS_PLUS_END_TODO}}##

!define PROJECT		"install"
!define VER_MAJOR		1
!define VER_MINOR		00

; Installer-Oberfl�che eindeutschen
SubCaption 0 ": Lizenz-Vereinbarung"
SubCaption 1 ": Installations-Optionen"
SubCaption 2 ": Installations-Verzeichnis"
SubCaption 3 ": Installiere Daten"
SubCaption 4 ": Vorgang beendet"
MiscButtonText "Zur�ck" "Weiter" "Abbrechen" "Schliessen"
InstallButtonText "Installieren"
SpaceTexts "Ben�tigter Speicher: " "Vorhandener Speicher: "
CompletedText "Vorgang beendet"
FileErrorText "Die Datei$\r$\n$0$\r$\nkonnte nicht erzeugt werden"
LicenseText "Lizenz-Vereinbarung:" "Ich stimme zu"
ComponentText "W�hlen Sie die gew�nschten Komponenten"  " "  " "
UninstallButtonText "Deinstallieren"
UninstallText "Details..." "Deinstallieren von:"
UninstallCaption "${PROJECT} deinstallieren"
UninstallSubCaption 0 "Best�tigung"
UninstallSubCaption 1 "Daten deinstallieren"
UninstallSubCaption 2 "Vorgang beendet"

SetOverwrite ifnewer
InstProgressFlags smooth
AutoCloseWindow false
ShowInstDetails show
ShowUninstDetails show
SilentInstall normal
SilentUnInstall normal

########################################################
################## Installations-Projekt  ########################
########################################################

; Name des Installers
Name "${PROJECT} V${VER_MAJOR}.${VER_MINOR} Installation"

; Ausgabe-Datei
OutFile "${PROJECT}${VER_MAJOR}${VER_MINOR}-Setup.exe"

Caption "${PROJECT} V${VER_MAJOR}.${VER_MINOR} Installation"

; Default-Installations-Verzeichnis
InstallDir $PROGRAMFILES\${PROJECT}
InstallDirRegKey HKLM "SOFTWARE\${PROJECT}" "Path"

; �berschrift des Dialoges zum Ausw�hlen des Installations-Verzeichnisses
DirText "Hiermit wird ${PROJECT} V${VER_MAJOR}.${VER_MINOR} installiert. W�hlen Sie das Verzeichnis, in dem Sie ${PROJECT} installiert haben bzw. installieren wollen." " " "Durchsuchen"

;LicenseData "${PROJECT}-Lizenz.txt
;InstType "Full (w/ Source and Contrib)"
;InstType "Normal (w/ Contrib, w/o Source)"
;InstType "Lite (w/o Source or Contrib)"

; Haupt-Sektion
Section "${PROJECT}"
	;SectionIn 1,2,3
	SetOutPath $INSTDIR
	File ${PROJECT}.exe
	SetOutPath $INSTDIR
	CreateDirectory $SMPROGRAMS\${PROJECT}
	CreateShortCut "$SMPROGRAMS\${PROJECT}\${PROJECT}.lnk" "$INSTDIR\${PROJECT}.exe"

	SetOutPath $INSTDIR
	CreateShortCut "$DESKTOP\${PROJECT}.lnk" "$INSTDIR\${PROJECT}.exe"

	SetOutPath $INSTDIR
	CreateShortCut "$QUICKSTART\${PROJECT}.lnk" "$INSTDIR\${PROJECT}.exe"

SectionEnd
