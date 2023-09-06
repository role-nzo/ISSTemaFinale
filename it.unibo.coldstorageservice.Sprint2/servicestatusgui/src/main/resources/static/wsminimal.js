/*
wsminimal.js
*/

    var socket;

    function sendMessage(message) {
        var jsonMsg = JSON.stringify( {'name': message});
        socket.send(jsonMsg);
        console.log("Sent Message: " + jsonMsg);
    }

    function connect(){
        var host     =  document.location.host;
        var pathname =  "/"                   //document.location.pathname;
        var addr     = "ws://" +host  + pathname + "socket"  ;
        //alert("connect addr=" + addr   );

        // Assicura che sia aperta un unica connessione
        if(socket !== undefined && socket.readyState !== WebSocket.CLOSED){
             alert("WARNING: Connessione WebSocket già stabilita");
        }
        socket = new WebSocket(addr);

        socket.onopen = function (event) {
            //console.log("Connected to " + addr);
            setMessageToWindow(infoDisplay,"socket | Connected to " + addr);
        };

        socket.onmessage = function (event) {
            //alert(`Got Message: ${event.data}`);
            msg = event.data;
            //alert(`Got Message: ${msg}`);
            console.log("ws-status:" + msg);

            if(msg.includes("depositdone")||msg.includes("clearcoldroomdone")){
                var fw = msg.split("(")[1].split(")")[0];
                document.getElementById("fw_real").innerHTML = fw;
            }//TO-DO Gestire messaggi ricevuti

            if( msg.includes("plan") ) setMessageToWindow(planexecDisplay,msg);
            //else if( msg.includes("RobotPos") ) setMessageToWindow(robotDisplay,msg);
            else setMessageToWindow(robotDisplay,msg); //""+`${event.data}`*/

            var fullMessage = msg.split("(")[1].split(")")[0];
            var message = fullMessage.split(",");
            var rejectedTickets = message[message.length - 1];
            var currentWeightReal = message[message.length - 2];
            var robotFree = message[message.length - 3];
            var posY = message[message.length - 4];
            var posX = message[message.length - 5];

            if(message.length > 5) {
                var map = fullMessage.split(posX)[0].dropLast(2);
            }
         };
    }//connect

connect();
