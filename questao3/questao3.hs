import Control.Concurrent
import Control.Concurrent.STM

type Marca = [Char]
type Nome = [Char]
type Capacidade = Int
type Maquina = [(Marca, Capacidade)]

litrosMarcaMaquina :: Marca -> Maquina -> Capacidade
litrosMarcaMaquina marca maquina = head [v | (m, v) <- maquina, m == marca]

refil :: Marca -> Capacidade -> Maquina -> Maquina
refil marca novaCapacidade maquina = ([(m, (if m == marca then novaCapacidade else v)) | (m, v) <- maquina])

empregado :: MVar Maquina -> Marca -> IO ()
empregado maquina marca = qualMaquina where qualMaquina = do 
                                            a <- takeMVar maquina
                                            if null a then do qualMaquina
                                            else do
                                                -- verifica qual refrigerante
                                                let litrosRestantes = litrosMarcaMaquina marca a
                                                if(litrosRestantes < 1000) then do
                                                    -- Se tiver menos litros do que o limite, refil
                                                    let novaCapacidade = litrosRestantes + 1000
                                                    threadDelay 1500000 -- Tempo para repor o refrigerante
                                                    let newA = refil marca novaCapacidade a
                                                    putStrLn ("Refrigerante " ++ marca  ++ " foi reabastecido com " ++ (show (novaCapacidade - litrosRestantes)) ++ " ml, e agora possui " ++ (show (novaCapacidade)) ++ " ml")
                                                    putMVar maquina newA
                                                    qualMaquina
                                                else do
                                                    -- Se não, libera a maquina
                                                    putMVar maquina a
                                                    qualMaquina

cliente :: MVar Maquina -> Marca -> Nome -> IO ()
cliente maquina marca nome = qualMaquina where qualMaquina = do
                                                        a <- takeMVar maquina
                                                        if null a then do qualMaquina
                                                        else do 
                                                            -- verifica qual refrigerante
                                                            let litrosRestantes = litrosMarcaMaquina marca a
                                                            if(litrosRestantes < 300) then do
                                                                -- Para evitar que o cliente retire mais do que a maquina possui
                                                                putMVar maquina a 
                                                                qualMaquina
                                                            else do 
                                                                -- Cliente enche o copo e libera a máquina
                                                                threadDelay 1000000 -- Tempo enchendo o copo
                                                                let novaCapacidade = max (litrosRestantes - 300) 0
                                                                let newA = refil marca novaCapacidade a
                                                                (print ("Cliente " ++ nome ++ " do refrigerante " ++ marca ++ " esta enchendo seu copo!"))
                                                                putMVar maquina newA
                                                                qualMaquina

main :: IO ()
main = do
    maquina <- newMVar [("Pepsie-Cola", 2000),("Guarana Polo Norte", 2000), ("Guarana Quate", 2000)]
    forkIO $ cliente maquina "Pepsie-Cola" "Matheus"
    forkIO $ cliente maquina "Guarana Polo Norte" "Bruno"
    forkIO $ cliente maquina "Guarana Polo Norte" "Pain"
    forkIO $ cliente maquina "Pepsie-Cola" "Maria"
    forkIO $ cliente maquina "Guarana Quate" "Josefina"
    forkIO $ cliente maquina "Pepsie-Cola" "Elizabeth"
    forkIO $ cliente maquina "Guarana Quate" "Joao"
    forkIO $ cliente maquina "Guarana Quate" "Andre"
    forkIO $ cliente maquina "Guarana Polo Norte" "Amaro"
    forkIO $ empregado maquina "Pepsie-Cola"
    forkIO $ empregado maquina "Guarana Polo Norte"
    forkIO $ empregado maquina "Guarana Quate"
    readLn
