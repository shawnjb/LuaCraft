@echo off
setlocal

REM Get the latest tag (both annotated and lightweight)
for /f "tokens=*" %%a in ('git tag --sort=-v:refname') do (
    if not defined latest_tag set latest_tag=%%a
)

if "%latest_tag%"=="" (
    echo No tags found!
    exit /b 1
)

echo Generating diff between the latest tag (%latest_tag%) and the current commit...

set output_file=diff_file.patch

git diff %latest_tag% HEAD > "%output_file%"

if exist "%output_file%" (
    echo Diff saved to %output_file%
) else (
    echo Failed to generate the diff!
)

endlocal
