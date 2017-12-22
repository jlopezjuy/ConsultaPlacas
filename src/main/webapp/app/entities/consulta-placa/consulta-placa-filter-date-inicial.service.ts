import { Injectable, Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'searchfilterReporteFechaInicial'
})

@Injectable()
export class SearchfilterReportePipeFechaInicial implements PipeTransform {

    transform(items: any, field: string, value?: any): any {
        if (value === undefined || value === null) return items;
        let fechaInicial = new Date(value.year + '-' + value.month + '-' + value.day);
        return items.filter(function (item) {
            return fechaInicial.getTime() <= item[field].getTime();
        });
    }
}