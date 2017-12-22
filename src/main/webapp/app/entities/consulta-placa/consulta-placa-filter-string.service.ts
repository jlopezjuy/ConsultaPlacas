import { Injectable, Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'searchfilterReporteString'
})

@Injectable()
export class SearchfilterReportePipeString implements PipeTransform {

    transform(items: any, field: string, value?: any): any {
        if (value === undefined) return items;
        return items.filter(function (item) {
            return item[field].toLowerCase().includes(value.toLocaleLowerCase());
        });
    }
}