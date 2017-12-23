import { Injectable, Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'searchfilterReporteNumber'
})

@Injectable()
export class SearchfilterReportePipeNumber implements PipeTransform {

    transform(items: any, field: string, value?: any): any {
        if (value === undefined || value == '') return items;
        return items.filter(function (item) {
            if (item[field] == value) return item[field];
        });
    }
}