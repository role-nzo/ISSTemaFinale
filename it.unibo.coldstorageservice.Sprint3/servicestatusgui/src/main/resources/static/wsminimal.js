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
            $.ajax({
                    type: "GET",
                    url: "/getmap",
                    success: console.log
            });



        };

        socket.onmessage = parse;
    }//connect

connect();


//testing

const canvas = document.getElementById("map");
const ctx = canvas.getContext("2d");

let map = ""
let posX = 0;
let posY = 0;


//parse({data: "status(|r, 1, 1, 1, 1, X,\n|1, 1, 1, 1, 1, X,\n|1, 1, X, 1, 1, X,\n|X, X, X, X, X, X,  2, 1, libero, 10, 3)"})

function parse (event) {
    let msg = event.data;
    console.log("ws-status:" + msg);

    if(msg.includes("transporttrolley")) {

        return;
    }

    /*if( msg.includes("plan") ) setMessageToWindow(planexecDisplay,msg);
    else setMessageToWindow(robotDisplay,msg);*/

    const fullMessage = msg.split("(")[1].split(")")[0];
    const message = fullMessage.split(",");

    if(fullMessage.includes("transporttrolleystatus")) {
        document.getElementById("led").setAttribute("class", message[0]);
        return;
    }

    const rejectedTickets = message[message.length - 1];
    const currentWeightReal = message[message.length - 2];
    const robotFree = message[message.length - 3];
    posY = message[message.length - 4];
    posX = message[message.length - 5];


    document.getElementById("stato").innerHTML = robotFree;
    document.getElementById("posizione").innerHTML = "( " + posX + " , " + posY + " )";
    document.getElementById("fw_real").innerHTML = currentWeightReal;
    document.getElementById("rifiuti").innerHTML = rejectedTickets;



    if(message.length > 5) {
        map = fullMessage.split(posX)[0].slice(0, -1);
    }

    writeCanvas();
}


window.onresize = writeCanvas

function writeCanvas() {
    let rows = map.split("|")

    let cellWidth = 0;
    let padding = 2;

    for (let i = 1; i < rows.length; i++) {
        let cells = rows[i].trim().split(",").filter(elem => elem !== '').map(elem => elem.trim())

        if(i === 1) {
            const computedStyle = getComputedStyle(canvas.parentElement);

            cellWidth = (canvas.parentElement.getBoundingClientRect().width - parseFloat(computedStyle.paddingLeft) - parseFloat(computedStyle.paddingRight)) / cells.length;
            canvas.width = cells.length * cellWidth;
            canvas.height  = (rows.length-1) * cellWidth;
        }

        for (let j = 0; j < cells.length; j++) {
            ctx.fillStyle = cells[j] === 'X' ? "#2f5f2f" : "#a4b85f";
            ctx.fillRect(cellWidth * j + ((cellWidth/100 * padding)/2), cellWidth * (i-1) + ((cellWidth/100 * padding)/2), cellWidth - ((cellWidth/100 * padding) * 2), cellWidth - ((cellWidth/100 * padding) * 2));
        }

    }

    padding++;
    ctx.drawImage(document.getElementById("source"), cellWidth * posX + ((cellWidth/100 * padding)/2), cellWidth * posY + ((cellWidth/100 * padding)/2), cellWidth - ((cellWidth/100 * padding) * 2), cellWidth - ((cellWidth/100 * padding) * 2))

}
