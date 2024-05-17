/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function getPeliculas()
{
    alert('Hola');
    $.ajax({
        url: 'Controller',
        data: {
            ACTION: 'PELICULAS.FIND_ALL'
        },
        dataType: 'json',
        success: function (responseText) {            
        }        
    });
}