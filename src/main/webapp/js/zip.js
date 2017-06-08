/*
 * Copyright 2009-2016 European Molecular Biology Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

!function(d) {
    var split_params = document.location.search.replace(/(^\?)/,'')
        .split("&")
        .filter(function (a) {return a!='' })
        .map(function(s) {
            s = s.split("=")
            v = decodeURIComponent(s[1]).split('+').join(' ');
            this[s[0]] =  this[s[0]] ? this[s[0]]+','+v:v;
            return this;
        }.bind({}));
    var params = split_params.length ? split_params[0] : {};


    params.retries = 0;
    setTimeout( function(){   checkStatus(params)} , Math.pow(2, params.retries) * 1000);
}(document);

function checkStatus(params){
    $.get( contextPath+"/"+params.dc+"/zipstatus", { filename: params.uuid}, function(data) {
        if(data) {
            switch (data.status) {
                case 'processing':
                    if (params.retries<8) params.retries++;
                    setTimeout( function(){   checkStatus(params)} , Math.pow(2, params.retries) * 1000);
                    break;
                case 'done':
                    link = contextPath+"/"+params.dc+"/files/"+params.accession+"/zip?file="+params.uuid;
                    $('#ftp-link').html('<a href="'+link+'">Click here to download the file</a>')
                    break;
                default:
                    err();
                    break;
            }
        } else {
            err();
        }
    })
    .fail(function() {
        err();
    })
}

function err() {
    $('#ftp-link').html('An error occured while preparing the archive. Please try again later.<br/> If the problem persists, please use the feedback form to report it.');
}

