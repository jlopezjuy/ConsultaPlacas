import { Injectable, Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'searchfilterReporteFechaFinal'
})

@Injectable()
export class SearchfilterReportePipeFechaFinal implements PipeTransform {

    transform(items: any, field: string, value?: any): any {        
        if (value === undefined || value === null) return items;
        let fechaFinal = new Date(value.year + "-" + value.month + "-" + value.day);
        return items.filter(function (item) {
            //console.log("Fecha del calendar: ", fechaFinal.getTime());
            //console.log("Fecha del array: ", item[field].getTime());
            return fechaFinal.getTime() >= item[field].getTime();
        });
    }
}