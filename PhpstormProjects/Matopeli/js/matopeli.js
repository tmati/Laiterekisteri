

var canvas = document.getElementById("myCanvas");
var ctx = canvas.getContext("2d");
ctx.fillStyle = 'white';

var mato = {
    xpos: 300,
    ypos: 200,
    suunta: "R",
    pituus: 0,
    perax: [],
    peray: []
};

var omena = {
    xpos: 0,
    ypos: 0,
};
var stop;
var reso = 20;
var fpsInterval, startTime, now, then, elapsed;


function alkutoimet(fps) {
    stop = false;
    mato.xpos = 300;
    mato.ypos = 200;
    mato.suunta = "R";
    mato.pituus = 0;
    mato.perax = [];
    mato.peray = [];
    fpsInterval = 1000 / fps;
    then = Date.now();
    startTime = then;
    arvoOmenapos();
    peliMain();
}


function peliMain () {
    if (!stop) {
        requestAnimationFrame(peliMain);
        now = Date.now();
        elapsed = now - then;
        if (elapsed > fpsInterval) {
            then = now - (elapsed % fpsInterval);
            matoSyo();
            piirra();
            kuolema();
            peranPaivitys();
            paivita();
        }
    }
}

function kuolema(){

    if (mato.xpos > canvas.width-reso || mato.ypos > canvas.height-reso || mato.ypos < 0 || mato.xpos < 0){
        cancelAnimationFrame(peliMain());
        overlay2();
        stop = true;
    }
    for (var i = 0; i < mato.perax.length; i++){
        if ((mato.xpos === mato.perax[i] && mato.ypos === mato.peray[i])){
            overlay2();
            stop = true;
            break;
        }
    }
}

function matoSyo(){
    if(mato.xpos === omena.xpos && mato.ypos === omena.ypos){
        mato.pituus++;
        arvoOmenapos();
    }
}

function piirra(){
    ctx.fillStyle = 'black';
    ctx.fillRect(0,0,600,400);
    ctx.fillStyle = 'white';
    for(var i = 0; i < mato.pituus; i++) {
        ctx.fillRect(mato.perax[i],mato.peray[i],reso,reso)
    }
    ctx.fillRect(mato.xpos, mato.ypos, reso, reso);
    ctx.fillStyle = 'red';
    ctx.fillRect(omena.xpos, omena.ypos,reso,reso);
}

function peranPaivitys(){
    if(mato.pituus === mato.perax.length){
        for(var i = 0;i < mato.perax.length-1; i++){
            mato.perax[i] = mato.perax[i+1];
            mato.peray[i] = mato.peray[i+1];
        }
    }
    mato.perax[mato.pituus-1] = mato.xpos;
    mato.peray[mato.pituus-1] = mato.ypos;
}

function paivita() {
        if (mato.suunta === "R") {
            mato.xpos = mato.xpos + reso;
        } else if (mato.suunta === "L") {
            mato.xpos = mato.xpos - reso;
        } else if (mato.suunta === "U") {
            mato.ypos = mato.ypos - reso;
        } else if (mato.suunta = "D") {
            mato.ypos = mato.ypos + reso;
        }

}

function arvoOmenapos(){
    omena.ypos = (Math.floor(Math.random()*canvas.height/reso+1)*reso)-reso;
    omena.xpos = (Math.floor(Math.random()*canvas.width/reso+1)*reso)-reso;
}

document.onkeydown = function (e) {
    e = e || window.event;
    if (e.key === 'w') {
        if (mato.suunta !== "D") {
            mato.suunta = "U";
        }
    }
    else if (e.key === 's') {
        if (mato.suunta !== "U") {
            mato.suunta = "D";
        }
    }
    else if (e.key === 'a') {
        if (mato.suunta !== "R") {
            mato.suunta = "L";
        }
    }
    else if (e.key === 'd') {
        if (mato.suunta !== "L") {
            mato.suunta = "R";
        }
    }
};



