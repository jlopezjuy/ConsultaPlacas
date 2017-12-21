import { Injectable, Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'searchfilterReporteFechaInicial'
})

@Injectable()
export class SearchfilterReportePipeFechaInicial implements PipeTransform {

    transform(items: any, field: string, value?: any): any {        
        if (value === undefined || value === null) return items;
        let fechaInicial = new Date(value.year + "-" + value.month + "-" + value.day);
        return items.filter(function (item) {
            //console.log("Fecha del calendar: ", fechaInicial.getTime());
            //console.log("Fecha del array: ", item[field].getTime());
            return fechaInicial.getTime() <= item[field].getTime();
        });
    }
}