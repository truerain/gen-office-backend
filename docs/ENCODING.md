# Encoding Policy

모든 소스 파일은 **UTF-8 (no BOM)** 으로 통일한다.

## 체크 방법 (PowerShell)
프로젝트 루트에서 아래 스크립트를 실행한다.

```powershell
$files = Get-ChildItem -Path src -Recurse -File -Filter "*.java" | Select-Object -ExpandProperty FullName;
$issues = @();
foreach ($f in $files) {
  $bytes = [System.IO.File]::ReadAllBytes($f);
  if ($bytes.Length -ge 3 -and $bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF) {
    $issues += "$f : BOM";
    continue;
  }
  try {
    $text = [System.Text.Encoding]::UTF8.GetString($bytes);
    if ($text -match "\uFFFD") {
      $issues += "$f : INVALID_UTF8";
    }
  } catch {
    $issues += "$f : INVALID_UTF8";
  }
}
if ($issues.Count -eq 0) { "CLEAN" } else { $issues }
```

## 자동 정리 (PowerShell)
BOM 제거 + UTF-8(no BOM)로 재저장.

```powershell
$files = Get-ChildItem -Path src -Recurse -File -Filter "*.java" | Select-Object -ExpandProperty FullName;
foreach ($f in $files) {
  $bytes = [System.IO.File]::ReadAllBytes($f);
  # 제거: UTF-8 BOM
  if ($bytes.Length -ge 3 -and $bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF) {
    $bytes = $bytes[3..($bytes.Length-1)];
  }
  $text = [System.Text.Encoding]::UTF8.GetString($bytes);
  [System.IO.File]::WriteAllText($f, $text, New-Object System.Text.UTF8Encoding($false));
}
```

## 주의
- 한글이 깨진 파일은 반드시 인코딩을 확인하고 UTF-8로 저장해야 한다.
- 한글 변환/수정 후에는 반드시 BOM 체크 스크립트를 실행한다.
