import Control.Concurrent
import Control.Concurrent.STM


pegaCapacidade marca maquina = head [v | (m, v) <- maquina, m == marca]

updateCapacidade marca novoCapacidade maquina = ([(m, (if m == marca then novoCapacidade else v)) | (m, v) <- maquina])


produtor cano marca = queCano where queCano = do 
                                                a <- takeMVar cano
                                                if null a then do queCano
                                                else do
                                                    -- ver o refrigerante que vai pegar
                                                    let capacidade = pegaCapacidade marca a
                                                    if(capacidade < 1000) then do
                                                        let newCapacidade = min (capacidade + 1000) 2000
                                                        threadDelay 1500000
                                                        let newA = updateCapacidade marca newCapacidade a
                                                        (print ("Refrigerante " ++ marca  ++ " foi reabastecido com " ++ (show (newCapacidade - capacidade)) ++ " ml, e agora possui " ++ (show (newCapacidade)) ++ " ml"))
                                                        putMVar cano newA
                                                        queCano
                                                    else do
                                                        putMVar cano a
                                                        queCano


consumidor cano marca nome = queCano where queCano = do
                                                        a <- takeMVar cano
                                                        if null a then do queCano
                                                        else do 
                                                            -- ver o refrigerante que vai pegar
                                                            let capacidade = pegaCapacidade marca a
                                                            if(capacidade < 300) then do
                                                                putMVar cano a 
                                                                queCano
                                                            else do 
                                                                threadDelay 1000000
                                                                let newCapacidade = max (capacidade - 300) 0
                                                                let newA = updateCapacidade marca newCapacidade a
                                                                (print ("Cliente " ++ nome ++ " do refrigerante " ++ marca ++ " esta enchendo seu copo!"))
                                                                putMVar cano newA
                                                                queCano
main :: IO ()
main = do
    maquina <- newMVar [("PCola", 2000),("Guarana Polo Norte", 2000), ("Guarana Quate", 2000)]
    forkIO $ consumidor maquina "PCola" "joao"
    forkIO $ consumidor maquina "Guarana Polo Norte" "maria"
    forkIO $ consumidor maquina "Guarana Quate" "neusa"
    forkIO $ consumidor maquina "PCola" "jose"
    forkIO $ produtor maquina "PCola"
    forkIO $ produtor maquina "Guarana Polo Norte"
    forkIO $ produtor maquina "Guarana Quate"
    readLn