{
  lib,
  maven,
}:

maven.buildMavenPackage rec {
  pname = "luacraft";
  version = "1.1.2";

  src = ./.;
  mvnHash = "sha256-tGEzmqgWwoVg/eGddt5Qs0u98phkuTjWvdwl37gxPww=";

  installPhase = ''
    runHook preInstall
    install -Dm600 target/LuaCraft-${version}.jar -t $out/bin/
    runHook postInstall
  '';

  meta = {
    description = "Execute Lua scripts on your Minecraft server. This is very experimental.";
    changelog = "https://github.com/dankfmemes/LuaCraft/releases/tag/${version}";
    homepage = "https://github.com/dankfmemes/LuaCraft";
    license = lib.licenses.asl20;
  };
}
