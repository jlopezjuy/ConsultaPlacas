import { URLSearchParams, BaseRequestOptions } from '@angular/http';

export const createRequestFilterOption = (req?: any): BaseRequestOptions => {
    const options: BaseRequestOptions = new BaseRequestOptions();
    if (req) {
        const params: URLSearchParams = new URLSearchParams();
        params.set('page', req.page);
        params.set('size', req.size);
        if (req.sort) {
            params.paramsMap.set('sort', req.sort);
        }
        params.set('query', req.query);
        params.set('issi', req.issi);
        params.set('municipio', req.municipio);
        params.set('corporacion', req.corporacion);
        params.set('estado', req.estado);
        params.set('desde', req.desde);
        params.set('hasta', req.hasta);

        options.params = params;
    }
    return options;
};
