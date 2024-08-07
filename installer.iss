[Setup]
AppName=SA Agreement Project
AppVersion=1.0
DefaultDirName={pf}\SAAgreementProject
DefaultGroupName=SA Agreement Project
OutputBaseFilename=SAAgreementProjectInstaller
SetupIconFile=san_yi_tang.ico
Compression=lzma
SolidCompression=yes

[Files]
Source: "target\SA31.jar"; DestDir: "{app}"; Flags: ignoreversion
Source: "san_yi_tang.ico"; DestDir: "{app}"; Flags: ignoreversion

[Icons]
Name: "{group}\SA Agreement Project"; Filename: "{app}\jre\bin\javaw.exe"; Parameters: "-jar {app}\SA31.jar"; WorkingDir: "{app}"; IconFilename: "{app}\san_yi_tang.ico"
Name: "{commondesktop}\SA Agreement Project"; Filename: "{app}\jre\bin\javaw.exe"; Parameters: "-jar {app}\SA31.jar"; WorkingDir: "{app}"; IconFilename: "{app}\san_yi_tang.ico"; Tasks: desktopicon

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Run]
Filename: "{app}\jre\bin\javaw.exe"; Parameters: "-jar {app}\SA31.jar"; WorkingDir: "{app}"; Description: "{cm:LaunchProgram,SA Agreement Project}"; Flags: nowait postinstall skipifsilent
