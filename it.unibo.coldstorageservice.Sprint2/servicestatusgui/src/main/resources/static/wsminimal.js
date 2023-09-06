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

        socket.onmessage = parse;
    }//connect

connect();


//testing
//parse({data: "status(|r, 1, 1, 1, 1, X,\n|1, 1, 1, 1, 1, X,\n|1, 1, X, 1, 1, X,\n|X, X, X, X, X, X,  0, 0, libero, 0, 0)"})


function parse (event) {
    //alert(`Got Message: ${event.data}`);
    msg = event.data;
    //alert(`Got Message: ${msg}`);
    console.log("ws-status:" + msg);

    if(msg.includes("depositdone")||msg.includes("clearcoldroomdone")){
        var fw = msg.split("(")[1].split(")")[0];
        document.getElementById("fw_real").innerHTML = fw;
    }//TO-DO Gestire messaggi ricevuti

    /*if( msg.includes("plan") ) setMessageToWindow(planexecDisplay,msg);
    else setMessageToWindow(robotDisplay,msg);*/
    const canvas = document.getElementById("map");
    const ctx = canvas.getContext("2d");


    var fullMessage = msg.split("(")[1].split(")")[0];
    var message = fullMessage.split(",");
    var rejectedTickets = message[message.length - 1];
    var currentWeightReal = message[message.length - 2];
    var robotFree = message[message.length - 3];
    var posY = message[message.length - 4];
    var posX = message[message.length - 5];

    if(message.length > 5) {
        var map = fullMessage.split(posX)[0].slice(0, -1);

        var rows = map.split("|")

        canvas.height  = rows.length * 100;

        for (let i = 1; i < rows.length; i++) {
            let cells = rows[i].trim().split(",").filter(elem => elem !== '').map(elem => elem.trim())

            if(i === 1)
                canvas.width  = cells.length * 100;

            for (let j = 0; j < cells.length; j++) {
                ctx.fillStyle = cells[j] === 'X' ? "red" : "black";
                ctx.fillRect(100 * j + 5, 100 * (i-1) + 5, 90, 90);
            }

        }
    }

    ctx.fillStyle = 'green'
    ctx.fillRect(100 * posX + 25, 100 * posY + 25, 50, 50);
 };
