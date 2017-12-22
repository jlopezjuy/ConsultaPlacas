import { Injectable, Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'searchfilterReporteFechaFinal'
})

@Injectable()
export class SearchfilterReportePipeFechaFinal implements PipeTransform {

    transform(items: any, field: string, value?: any): any {
        if (value === undefined || value === null) return items;
        let fechaFinal = new Date(value.year + '-' + value.month + '-' + value.day + 'T23:59:59');
        return items.filter(function(item) {
            return fechaFinal.getTime() >= item[field].getTime();
        });
    }
}