;NSIS Modern User Interface
;Basic Example Script
;Written by Joost Verburg

;--------------------------------
;Include Modern UI

  !include "MUI2.nsh"

;--------------------------------
;General

  ;Name and file
  Name "#YOGO Platform"
  OutFile "YOGO Platform.exe"

  ;Default installation folder
  InstallDir "$LOCALAPPDATA\YOGO Platform"
  
  ;Get installation folder from registry if available
  InstallDirRegKey HKLM "Software\YOGO Platform" ""

  ;Request application privileges for Windows Vista
  RequestExecutionLevel user

;--------------------------------
;Interface Settings

  !define MUI_ABORTWARNING

;--------------------------------
;Pages

  !insertmacro MUI_PAGE_LICENSE ".\License.txt"
  !insertmacro MUI_PAGE_DIRECTORY
  
  !define MUI_STARTMENUPAGE_DEFAULTFOLDER "#YOGO Platform"
  !define MUI_STARTMENUPAGE_NODISABLE
  !insertmacro MUI_PAGE_STARTMENU idStartmenu ${MUI_STARTMENUPAGE_VARIABLE}

  !insertmacro MUI_PAGE_INSTFILES
  
  !define MUI_FINISHPAGE_SHOWREADME "$INSTDIR\ReadMe.txt"
  !insertmacro MUI_PAGE_FINISH
  
  
  !insertmacro MUI_UNPAGE_CONFIRM
  
  !insertmacro MUI_UNPAGE_INSTFILES
  
;--------------------------------
;Languages
 
  !insertmacro MUI_LANGUAGE "English"

;--------------------------------
;Installer Sections

Section "Install"

  SetOutPath "$INSTDIR"
  
  ;ADD YOUR OWN FILES HERE...
  
  ;Store installation folder
  WriteRegStr HKLM "Software\YOGO Platform" "" $INSTDIR
  
  ;Create uninstaller
  WriteUninstaller "$INSTDIR\Uninstall.exe"

SectionEnd

;--------------------------------
;Uninstaller Section

Section "Uninstall"

  ;ADD YOUR OWN FILES HERE...

  Delete "$INSTDIR\Uninstall.exe"

  RMDir "$INSTDIR"

  DeleteRegKey /ifempty HKLM "Software\YOGO Platform"

SectionEnd