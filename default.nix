{
  lib,
  maven,
}:

maven.buildMavenPackage rec {
  pname = "luacraft";
  version = "1.1.3";

  src = ./.;
  mvnHash = "sha256-kBdWkjsssuGkwawhPdwmeBhu81TSSDHACKFVGDl6lhk=";

  installPhase = ''
    runHook preInstall
    install -Dm600 target/LuaCraft-${version}.jar -t $out/bin/
    runHook postInstall
  '';

  meta = {
    description = "Execute Lua scripts on your Minecraft server. This is very experimental.";
    changelog = "https://github.com/shawnjb/LuaCraft/releases/tag/${version}";
    homepage = "https://github.com/shawnjb/LuaCraft";
    license = lib.licenses.asl20;
  };
}
