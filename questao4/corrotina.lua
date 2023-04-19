pikachu={}
pikachu["nome"] = "Pikachu"
pikachu["hp"] = 800
pikachu["ataques"] = {}
pikachu["ataques"]["choque do trovao"] = 50
pikachu["ataques"]["calda de ferro"] = 100
pikachu["ataques"]["investida trovao"] = 150
pikachu["ataques"]["trovao"] = 200

raichu={}
raichu["nome"] = "Raichu"
raichu["hp"] = 1000
raichu["ataques"] = {}
raichu["ataques"]["choque do trovao"] = 50
raichu["ataques"]["calda de ferro"] = 100
raichu["ataques"]["investida trovao"] = 150
raichu["ataques"]["trovao"] = 200

function resumePikachu ()
  coroutine.resume(pikachuCorrotina)
end

function resumeRaichu ()
  coroutine.resume(raichuCorrotina)
end

function encerraAtaquePikachu ()
  coroutine.yield()
end

function encerraAtaqueRaichu ()
  coroutine.yield()
end

batalha = coroutine.create(
  function ()
    while (raichu["hp"] > 0) and (pikachu["hp"] > 0) do
      resumePikachu()
      resumeRaichu()
    end
  end
)

pikachuCorrotina = coroutine.create(
  function ()
    while (raichu["hp"] > 0) and (pikachu["hp"] > 0) do
      qualAtaque = math.random(20)
      if (qualAtaque >= 1) and (qualAtaque <= 10) then
        ataque = "choque do trovao"
      elseif (qualAtaque >= 11) and (qualAtaque <= 15) then
        ataque = "calda de ferro"
      elseif (qualAtaque >= 16) and (qualAtaque <= 18) then
        ataque = "investida trovao"
      elseif (qualAtaque >= 19) and (qualAtaque <= 20) then
        ataque = "trovao"
      end

      raichu["hp"] = raichu["hp"] - pikachu["ataques"][ataque]
      print("Pikachu usou " .. ataque .. " e tem " .. pikachu["hp"] .. " de vida. Raichu tem ".. raichu["hp"] .. " de vida.")
      encerraAtaquePikachu()
    end
  end
)

raichuCorrotina = coroutine.create(
  function ()
    while (raichu["hp"] > 0) and (pikachu["hp"] > 0) do
      qualAtaque = math.random(20)
      if (qualAtaque >= 1) and (qualAtaque <= 10) then
        ataque = "choque do trovao"
      elseif (qualAtaque >= 11) and (qualAtaque <= 15) then
        ataque = "calda de ferro"
      elseif (qualAtaque >= 16) and (qualAtaque <= 18) then
        ataque = "investida trovao"
      elseif (qualAtaque >= 19) and (qualAtaque <= 20) then
        ataque = "trovao"
      end

      pikachu["hp"] = pikachu["hp"] - raichu["ataques"][ataque]
      print("Raichu usou " .. ataque .. " e tem " .. raichu["hp"] .. " de vida. Pikachu tem ".. pikachu["hp"] .. " de vida.")
      encerraAtaqueRaichu()
    end
  end
)

coroutine.resume(batalha)
