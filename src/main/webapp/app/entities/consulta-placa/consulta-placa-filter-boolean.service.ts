import { Injectable, Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'searchfilterReporteBoolean'
})

@Injectable()
export class SearchfilterReportePipeBoolean implements PipeTransform {

    transform(items: any, field: string, value?: any): any {
        if (value === undefined) return items;
        return items.filter(function (item) {
            if (value === 'Positivo') {
                return item[field] === true;
            } else {
                if (value === 'Negativo') {
                    return item[field] === false;
                } else {
                    return item[field] === true || item[field] === false;
                }
            }
        });
    }
}